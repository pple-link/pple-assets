package link.pple.assets.configuration.security.oauth

import link.pple.assets.domain.account.entity.Account
import org.springframework.stereotype.Service

@Service
class OAuth2ProviderAdjuster {

    fun adjust(clientName: String, attributes: Map<String, Any>): OAuth2Profile {
        return when (Account.ProviderType.from(clientName)) {
            Account.ProviderType.KAKAO -> OAuth2Profile.Kakao(attributes)
        }
    }
}
