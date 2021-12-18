package link.pple.assets.configuration.security

import io.jsonwebtoken.*
import link.pple.assets.configuration.AppProperties
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.security.SignatureException
import java.util.*
import io.jsonwebtoken.UnsupportedJwtException as UnsupportedJwtException1


@Service
class TokenProvider(
    private val appProperties: AppProperties
) {

    fun createToken(authentication: Authentication): String {
        val principal: PrincipalDetails = authentication.principal as PrincipalDetails
        val now = Date()
        val expiryDate = Date(now.time + appProperties.auth.tokenExpirationMillis)
        return Jwts.builder()
            .setSubject(principal.identifier)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(SignatureAlgorithm.HS512, appProperties.auth.tokenSecret)
            .compact()
    }

    fun getIdentifierFromToken(token: String): Long {
        val claims: Claims = Jwts.parser()
            .setSigningKey(appProperties.auth.tokenSecret)
            .parseClaimsJws(token)
            .body
        return claims.subject.toLong()
    }

    fun validateToken(authToken: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(appProperties.auth.tokenSecret).parseClaimsJws(authToken)
            return true
        } catch (ex: SignatureException) {
            log.error("Invalid JWT signature")
        } catch (ex: MalformedJwtException) {
            log.error("Invalid JWT token")
        } catch (ex: ExpiredJwtException) {
            log.error("Expired JWT token")
        } catch (ex: UnsupportedJwtException1) {
            log.error("Unsupported JWT token")
        } catch (ex: IllegalArgumentException) {
            log.error("JWT claims string is empty.")
        }
        return false
    }

    companion object {
        private val log: Logger = LoggerFactory.getLogger(TokenProvider::class.java)
    }
}
