package link.pple.assets.configuration.security

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

/**
 * Trigger: loginProcessingUrl at Security Configuration
 *  - UserDetailsService Type Component 의 loadUserByUsername 실행
 */
@Deprecated(message = "UnsupportedOperation")
class PrincipalDetailsService : UserDetailsService {

    /**
     * username 에 해당하는 Account 가 존재하는지 확인 -> UserDetails 인스턴스 생성해 리턴
     */
    override fun loadUserByUsername(username: String): UserDetails? {
        throw UnsupportedOperationException()
    }
}
