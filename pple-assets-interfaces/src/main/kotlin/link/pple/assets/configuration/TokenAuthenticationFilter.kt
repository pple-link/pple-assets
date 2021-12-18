package link.pple.assets.configuration

import link.pple.assets.configuration.security.TokenProvider
import link.pple.assets.domain.account.entity.Account
import link.pple.assets.domain.account.service.AccountQuery
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class TokenAuthenticationFilter(
    private val tokenProvider: TokenProvider,
    private val accountQuery: AccountQuery
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwt = getJwtFromRequest(request)
            if (jwt != null && tokenProvider.validateToken(jwt)) {
                val identifier = tokenProvider.getIdentifierFromToken(jwt)
                val account: Account = accountQuery.findById(identifier)

                val authorities = mutableListOf(
                    GrantedAuthority { account.role.name }
                )
                val authentication = UsernamePasswordAuthenticationToken(account, null, authorities)
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (ex: Exception) {
            logger.error("Could not set user authentication in security context", ex)
        }
        filterChain.doFilter(request, response)
    }

    private fun getJwtFromRequest(request: HttpServletRequest): String? {
        val bearerToken: String? = request.getHeader("Authorization")
        return if (bearerToken != null && bearerToken.isNotBlank() && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(7, bearerToken.length)
        } else null
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(TokenAuthenticationFilter::class.java)
    }
}
