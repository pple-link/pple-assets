package link.pple.assets.configuration.security

import link.pple.assets.configuration.TokenAuthenticationFilter
import link.pple.assets.configuration.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository
import link.pple.assets.configuration.security.oauth.PrincipalOAuth2UserService
import link.pple.assets.configuration.security.oauth.handler.OAuth2AuthenticationFailureHandler
import link.pple.assets.configuration.security.oauth.handler.OAuth2AuthenticationSuccessHandler
import link.pple.assets.domain.account.service.AccountQuery
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
/**
 * secured 어노테이션 활성화 / preAuthorize, postAuthorize 어노테이션 활성화
 * 특정 하나의 메소드만 권한을 걸고 싶을 때 사용. secured 권장
 */
//@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
class SecurityConfig(
    private val oAuth2UserService: PrincipalOAuth2UserService,
    private val tokenProvider: TokenProvider,
    private val accountQuery: AccountQuery,
    private val oAuth2AuthenticationSuccessHandler: OAuth2AuthenticationSuccessHandler,
    private val oAuth2AuthenticationFailureHandler: OAuth2AuthenticationFailureHandler
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http.sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .csrf().disable()
            .httpBasic().disable()
            .formLogin().disable()
            .logout().logoutUrl("/logout")
            .and()
            .authorizeRequests()
            .antMatchers("/hello/**").authenticated()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/admin/**").hasAuthority("ADMIN")
            .anyRequest().permitAll()
            .and()
            .oauth2Login().defaultSuccessUrl("/hello")
            .userInfoEndpoint().userService(oAuth2UserService)
            .and()
            .successHandler(oAuth2AuthenticationSuccessHandler)
            .failureHandler(oAuth2AuthenticationFailureHandler)

        http.addFilterBefore(tokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    fun cookieAuthorizationRequestRepository(): HttpCookieOAuth2AuthorizationRequestRepository {
        return HttpCookieOAuth2AuthorizationRequestRepository()
    }

    @Bean
    fun tokenAuthenticationFilter(): TokenAuthenticationFilter? {
        return TokenAuthenticationFilter(
            tokenProvider = tokenProvider,
            accountQuery = accountQuery
        )
    }


}
