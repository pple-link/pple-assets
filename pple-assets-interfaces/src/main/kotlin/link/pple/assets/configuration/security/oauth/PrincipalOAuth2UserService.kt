package link.pple.assets.configuration.security.oauth

import link.pple.assets.configuration.security.PrincipalDetails
import link.pple.assets.domain.account.entity.Account
import link.pple.assets.domain.account.service.AccountCommand
import link.pple.assets.domain.account.service.AccountDefinition
import link.pple.assets.domain.account.service.AccountQuery
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.stereotype.Service

/**
 * OAuth2 Sign-up Process
 *    1. OAuth2 Login 완료 후 후처리: 코드 수신(인증)
 *    2. 액세스 토큰 발행(권한)
 *    3. 사용자 프로필 정보 조회
 *    4. 회원가입
 *      - (Optional) 회원가입 시 필요하다면 추가 정보 입력 받기
 */
@Service
class PrincipalOAuth2UserService(
    private val oAuth2ProviderAdjuster: OAuth2ProviderAdjuster,
    private val accountQuery: AccountQuery,
    private val accountCommand: AccountCommand
) : DefaultOAuth2UserService() {

    /**
     * 1. Google Login Button Click
     * 2. Google Login Pop-up
     * 3. Login Success
     * 4. return Code
     * 5. request AccessToken
     * 4, 5번을 OAuth2-Client 에서 해줌
     */
    override fun loadUser(userRequest: OAuth2UserRequest): PrincipalDetails {

        val clientName: String = userRequest.clientRegistration.clientName
        val attributes: Map<String, Any> = super.loadUser(userRequest).attributes

        val oAuth2Profile = oAuth2ProviderAdjuster.adjust(
            clientName = clientName,
            attributes = attributes
        )

        val account = accountQuery.getByEmailOrNull(oAuth2Profile.email)
            ?: createNewAccount(oAuth2Profile = oAuth2Profile)

        return PrincipalDetails(
            account = account,
            attributes = attributes
        )
    }

    private fun createNewAccount(oAuth2Profile: OAuth2Profile): Account {
        val definition = AccountDefinition(
            key = Account.ProviderKey(
                type = oAuth2Profile.type,
                id = oAuth2Profile.id
            ),
            email = oAuth2Profile.email,
            displayName = oAuth2Profile.displayName
        )

        return accountCommand.create(definition = definition)
    }
}
