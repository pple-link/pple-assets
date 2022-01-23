package link.pple.assets.infrastructure.alarm.sms

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/**
 * @Author Heli
 */
@Component
class NurigoAppProperties {

    @Value("\${solapi.apiKey}")
    lateinit var apiKey: String

    @Value("\${solapi.apiSecretKey}")
    lateinit var apiSecretKey: String

    @Value("\${solapi.fromPhoneNumber}")
    lateinit var fromPhoneNumber: String

    val domain = "https://api.solapi.com"
}
