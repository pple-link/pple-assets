package link.pple.assets.configuration.security

import link.pple.assets.domain.account.entity.Account
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
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
) : UserDetails, OAuth2User {

    /**
     * implements OAuth2User
     */
    override fun getName() = ""

    override fun getAttributes() = this.attributes

    // ========================

    /**
     * implements UserDetails
     */
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return mutableListOf<GrantedAuthority>().also { collection ->
            collection.add(GrantedAuthority {
                account.role.name
            })
        }
    }

    override fun getUsername(): String {
        return account.email
    }

    override fun getPassword(): String {
        return account.password
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}
