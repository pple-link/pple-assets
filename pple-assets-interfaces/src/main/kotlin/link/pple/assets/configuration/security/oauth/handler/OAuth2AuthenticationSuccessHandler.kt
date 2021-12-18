package link.pple.assets.configuration.security.oauth.handler

import link.pple.assets.configuration.AppProperties
import link.pple.assets.configuration.security.TokenProvider
import link.pple.assets.configuration.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository
import link.pple.assets.configuration.security.oauth.HttpCookieOAuth2AuthorizationRequestRepository.Companion.REDIRECT_URI_PARAM_COOKIE_NAME
import link.pple.assets.util.CookieUtils.getCookie
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class OAuth2AuthenticationSuccessHandler(
    private val httpCookieOAuth2AuthorizationRequestRepository: HttpCookieOAuth2AuthorizationRequestRepository,
    private val tokenProvider: TokenProvider,
    private val appProperties: AppProperties
) : SimpleUrlAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ) {
        val targetUrl = determineTargetUrl(request, response, authentication)
        if (response.isCommitted) {
            logger.debug("Response has already been committed. Unable to redirect to $targetUrl")
            return
        }
        clearAuthenticationAttributes(request, response)
        redirectStrategy.sendRedirect(request, response, targetUrl)
    }

    override fun determineTargetUrl(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authentication: Authentication
    ): String {
        val redirectUri: String? = getCookie(request, REDIRECT_URI_PARAM_COOKIE_NAME)?.value

        if (redirectUri != null && !isAuthorizedRedirectUri(redirectUri)) {
            throw Exception("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication")
        }
        val targetUrl: String = redirectUri ?: defaultTargetUrl
        val token: String = tokenProvider.createToken(authentication)

        return UriComponentsBuilder.fromUriString(targetUrl)
            .queryParam("token", token)
            .build().toUriString()
    }

    protected fun clearAuthenticationAttributes(request: HttpServletRequest?, response: HttpServletResponse?) {
        super.clearAuthenticationAttributes(request)
        httpCookieOAuth2AuthorizationRequestRepository!!.removeAuthorizationRequestCookies(request!!, response!!)
    }

    private fun isAuthorizedRedirectUri(uri: String): Boolean {
        val clientRedirectUri: URI = URI.create(uri)
        return appProperties.oauth2.authorizedRedirectUris
            .stream()
            .anyMatch { authorizedRedirectUri ->
                val authorizedURI: URI = URI.create(authorizedRedirectUri)
                if (authorizedURI.host.equals(clientRedirectUri.host, ignoreCase = true)
                    && authorizedURI.port == clientRedirectUri.port
                ) {
                    return@anyMatch true
                }
                false
            }
    }

}
