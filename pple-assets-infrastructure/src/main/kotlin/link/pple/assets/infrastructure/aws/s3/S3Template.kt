package link.pple.assets.infrastructure.aws.s3

import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.future.asDeferred
import kotlinx.coroutines.future.await
import link.pple.assets.util.getOnlyFileName
import software.amazon.awssdk.core.async.AsyncRequestBody
import software.amazon.awssdk.services.s3.S3AsyncClient
import software.amazon.awssdk.services.s3.model.*
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths

/**
 * @Author Heli
 */
class S3Template(
    private val s3Client: S3AsyncClient,
) {
    suspend fun downloadAll(bucket: String, prefix: String, outputFolderPath: Path) {
        val files = getObjectList(bucket, prefix)

        files.asSequence()
            .chunked(10)
            .forEach { windowedFiles ->
                windowedFiles.map {
                    s3Client.getObject(
                        GetObjectRequest.builder()
                            .apply {
                                bucket(bucket)
                                key(it)
                            }
                            .build(),
                        Paths.get(outputFolderPath.toString() + File.separator + it.getOnlyFileName())
                    ).asDeferred()
                }.awaitAll()
            }
    }

    suspend fun upload(
        bucket: String,
        s3Prefix: String,
        fileName: String,
        bytes: ByteArray
    ): PutObjectResponse {
        val request = PutObjectRequest.builder()
            .apply {
                bucket(bucket)
                key("$s3Prefix/${fileName}")
            }.build()
        val requestBody = AsyncRequestBody.fromBytes(bytes)
        return s3Client.putObject(request, requestBody).await()
    }

    suspend fun upload(bucket: String, s3Prefix: String, files: Array<File>): List<UploadPartResponse> {
        return files.map { file ->
            val request = UploadPartRequest.builder()
                .apply {
                    bucket(bucket)
                    key("$s3Prefix/${file.name}")
                }.build()
            s3Client.uploadPart(request, file.toPath()).asDeferred()
        }.awaitAll()
    }

    suspend fun move(
        bucket: String,
        sourcePrefix: String,
        destinationPrefix: String,
        excludeKeys: List<String> = emptyList(),
    ) {
        val files = getObjectList(bucket, sourcePrefix).filterNot { excludeKeys.contains(it.getOnlyFileName()) }

        files.forEach {
            listOf(
                s3Client.copyObject { builder ->
                    builder.apply {
                        copySourceIfMatch("$bucket/$it")
                        destinationBucket(bucket)
                        destinationKey(destinationPrefix + "/" + it.getOnlyFileName())
                    }
                }.asDeferred(),
                s3Client.deleteObject { builder ->
                    builder.apply {
                        bucket(bucket)
                        key(it)
                    }
                }.asDeferred()
            ).awaitAll()
        }
    }

    private suspend fun getObjectList(bucket: String, prefix: String): List<String> {
        val files = mutableListOf<String>()
        var request = ListObjectsV2Request.builder()
            .apply {
                bucket(bucket)
                prefix(prefix)
            }.build()

        do {
            val response = s3Client.listObjectsV2(request).await()
            response.contents().forEach {
                files += it.key()
            }
            request = request.copy {
                it.continuationToken(response.nextContinuationToken())
            }
        } while (response.isTruncated)

        return files
    }
}
