package link.pple.assets.configuration.security

import link.pple.assets.domain.account.entity.Account
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

    override fun getName() = account.email

    override fun getAttributes() = this.attributes

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return mutableListOf<GrantedAuthority>().also { collection ->
            collection.add(GrantedAuthority {
                account.role.name
            })
        }
    }
}
