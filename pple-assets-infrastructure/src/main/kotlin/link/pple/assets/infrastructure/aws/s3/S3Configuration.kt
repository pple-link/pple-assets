package link.pple.assets.infrastructure.aws.s3

import link.pple.assets.infrastructure.aws.AwsCredentials
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3AsyncClient

/**
 * @Author Heli
 */
@Configuration
class S3Configuration(
    private val awsCredentials: AwsCredentials
) {

    @Bean
    fun s3Template() = S3Template(
        s3Client = s3Client()
    )

    fun s3Client(): S3AsyncClient =
        S3AsyncClient.builder()
            .apply {
                credentialsProvider(
                    StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(
                            awsCredentials.accessKey,
                            awsCredentials.secretKey
                        )
                    )
                )
                region(Region.of(awsCredentials.region))
            }
            .build()
}
