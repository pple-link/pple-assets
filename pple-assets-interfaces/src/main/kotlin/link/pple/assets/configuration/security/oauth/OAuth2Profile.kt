package link.pple.assets.configuration.security.oauth

import link.pple.assets.domain.account.entity.Account
import link.pple.assets.util.notNull

sealed class OAuth2Profile {

    abstract val type: Account.ProviderType
    abstract val id: String
    abstract val email: String
    abstract val displayName: String
    abstract val profileImageUrl: String

    /**
     * 1. id
     * 2. kakao_account -> profile -> nickname
     * 3. kakao_account -> profile -> profile_image_url
     * 4. kakao_account -> email
     */
    data class Kakao(
        val attributes: Map<String, Any>
    ) : OAuth2Profile() {

        override val type: Account.ProviderType = Account.ProviderType.KAKAO

        @Suppress("UNCHECKED_CAST")
        private val kakaoAccount: Map<String, Any> = attributes["kakao_account"].notNull {
            "OAuth2Profile.Kakao kakao_account can not be parsed [${attributes}]"
        } as Map<String, Any>

        @Suppress("UNCHECKED_CAST")
        private val profile: Map<String, Any> = kakaoAccount["profile"].notNull {
            "OAuth2Profile.Kakao kakao_account.profile can not be parsed [${attributes}]"
        } as Map<String, Any>

        override val id: String
            get() = attributes["id"]
                .notNull { "OAuth2Profile.Kakao id can not be parsed [${attributes}]" }
                .toString()

        override val email: String
            get() = kakaoAccount["email"]
                .notNull { "OAuth2Profile.Kakao kakaoAccount.email can not be parsed [${attributes}]" }
                .toString()

        override val displayName: String
            get() = profile["nickname"]
                .notNull { "OAuth2Profile.Kakao profile.nickname can not be parsed [${attributes}]" }
                .toString()

        override val profileImageUrl: String
            get() = profile["profile_image_url"]
                .notNull { "OAuth2Profile.Kakao profile.profile_image_url can not be parsed [${attributes}]" }
                .toString()
    }
}
