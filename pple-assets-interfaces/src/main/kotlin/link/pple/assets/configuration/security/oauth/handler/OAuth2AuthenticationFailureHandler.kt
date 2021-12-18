package link.pple.assets.configuration.security.oauth.handler

import link.pple.assets.configuration.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository
import link.pple.assets.configuration.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository.Companion.REDIRECT_URI_PARAM_COOKIE_NAME
import link.pple.assets.util.CookieUtils.getCookie
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class OAuth2AuthenticationFailureHandler(
    private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository
) : SimpleUrlAuthenticationFailureHandler() {

    override fun onAuthenticationFailure(
        request: HttpServletRequest,
        response: HttpServletResponse,
        exception: org.springframework.security.core.AuthenticationException
    ) {
        var targetUrl: String = getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)?.value ?: "/"

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
            .queryParam("error", exception.localizedMessage)
            .build().toUriString()

        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response)
        redirectStrategy.sendRedirect(request, response, targetUrl)
    }
}
