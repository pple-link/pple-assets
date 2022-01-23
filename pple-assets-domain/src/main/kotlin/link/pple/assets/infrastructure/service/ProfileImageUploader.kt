package link.pple.assets.infrastructure.service

import kotlinx.coroutines.runBlocking
import link.pple.assets.infrastructure.aws.s3.S3Template
import org.apache.tika.mime.MimeTypes
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*


/**
 * @Author Heli
 */
@Service
class ProfileImageUploader(
    private val s3Template: S3Template
) {

    @Value("\${aws.s3.bucket:}")
    private lateinit var bucket: String

    fun upload(multipartFile: MultipartFile): S3UploadResponse {
        val newFileName = "${UUID.randomUUID()}-${System.nanoTime()}${multipartFile.extension}"

        val response = runBlocking {
            s3Template.upload(
                bucket = bucket,
                s3Prefix = PROFILE_IMAGE_S3_PREFIX,
                fileName = newFileName,
                bytes = multipartFile.bytes
            )
        }

        return S3UploadResponse(
            entityTag = response.eTag().replace("\"", ""),
            fileName = newFileName
        )
    }

    private val MultipartFile.extension: String
        get() {
            val contentType = this.contentType
            val allType = MimeTypes.getDefaultMimeTypes()
            val mimeType = allType.forName(contentType)
            return mimeType.extension
        }

    companion object {
        private const val PROFILE_IMAGE_S3_PREFIX = "profile"
    }
}
