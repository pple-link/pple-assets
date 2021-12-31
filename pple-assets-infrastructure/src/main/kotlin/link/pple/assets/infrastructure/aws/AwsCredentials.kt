package link.pple.assets.infrastructure.aws

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * @Author Heli
 */
@Component
class AwsCredentials {

    @Value("\${aws.credentials.accessKey}")
    lateinit var accessKey: String

    @Value("\${aws.credentials.secretKey}")
    lateinit var secretKey: String

    val region: String = "ap-northeast-2"
}
