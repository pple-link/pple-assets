package link.pple.assets.configuration.security

import link.pple.assets.configuration.security.oauth.PrincipalOAuth2UserService
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@EnableWebSecurity
/**
 * secured 어노테이션 활성화 / preAuthorize, postAuthorize 어노테이션 활성화
 * 특정 하나의 메소드만 권한을 걸고 싶을 때 사용. secured 권장
 */
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class SecurityConfiguration(
    private val oAuth2UserService: PrincipalOAuth2UserService
) : WebSecurityConfigurerAdapter() {

    @Bean
    fun encodePwd(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/admin/**").hasAnyRole("ADMIN")
            .anyRequest().permitAll()
            .and()
            .csrf().disable()
            .httpBasic().disable()
            .formLogin().disable()
            .logout().disable()
            .oauth2Login()
            .loginPage("/oauth2/...???")
            .userInfoEndpoint()
            .userService(oAuth2UserService)
    }
}
