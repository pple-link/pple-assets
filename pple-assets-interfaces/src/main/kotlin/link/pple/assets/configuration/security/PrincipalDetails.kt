package link.pple.assets.configuration.security

import link.pple.assets.domain.account.entity.Account
import link.pple.assets.domain.configuration.jpa.requiredId
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.OAuth2User

/**
 * Security Session
 * Security ContextHolder -> Authentication -> UserDetails -> GrantedAuthority
 *                                             : Common Login
 *                                          -> OAuth2User
 *                                             : OAuth2 Login
 * class CommonUserContext: UserDetails, OAuth2User
 */
class PrincipalDetails(
    private val account: Account,
    private val attributes: Map<String, Any>
) : OAuth2User {

    val identifier: String
        get() = account.requiredId.toString()

    override fun getName() = account.email

    override fun getAttributes() = this.attributes

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(
            GrantedAuthority { account.role.name }
        )
    }
}
