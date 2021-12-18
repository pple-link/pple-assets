package link.pple.assets.configuration.security

import link.pple.assets.configuration.security.oauth.PrincipalOAuth2UserService
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter

@EnableWebSecurity
/**
 * secured 어노테이션 활성화 / preAuthorize, postAuthorize 어노테이션 활성화
 * 특정 하나의 메소드만 권한을 걸고 싶을 때 사용. secured 권장
 */
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class SecurityConfig(
    private val oAuth2UserService: PrincipalOAuth2UserService
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
            .httpBasic().disable()
            .formLogin().disable()
            .logout().disable()
            .authorizeRequests()
            .antMatchers("/hello/**").authenticated()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/admin/**").hasAnyRole("ADMIN")
            .anyRequest().permitAll()
            .and().oauth2Login().defaultSuccessUrl("/hello")
            .userInfoEndpoint().userService(oAuth2UserService)
    }
}
