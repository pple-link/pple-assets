package link.pple.assets.configuration.auditor

import link.pple.assets.configuration.jpa.Auditor
import link.pple.assets.configuration.jpa.Auditor.Companion.SYSTEM_ID
import link.pple.assets.configuration.jpa.Auditor.Companion.ofSystem
import link.pple.assets.configuration.jpa.AuditorHolder
import link.pple.assets.configuration.jpa.requiredId
import link.pple.assets.domain.account.service.AccountQuery
import link.pple.assets.util.notNull
import org.springframework.core.annotation.AnnotatedElementUtils
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @Author Heli
 */
class AuditorInterceptor(
    private val accountQuery: AccountQuery
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val handlerMethod = handler as HandlerMethod

        val auditingApi = handlerMethod.getAnnotation<AuditingApi>()

        if (auditingApi != null) {
            resolveAuditor(request)
        }

        return true
    }

    private fun resolveAuditor(request: HttpServletRequest) {
        val accountUuidHeaderValue = request.getHeader(ACCOUNT_UUID_HEADER_NAME)
            .notNull { "$ACCOUNT_UUID_HEADER_NAME is required [${request.requestURI}]" }

        val auditor = if (accountUuidHeaderValue == SYSTEM_ID.toString()) {
            ofSystem()
        } else {
            val account = accountQuery.getByUuid(uuid = accountUuidHeaderValue)
            Auditor(account.requiredId)
        }

        AuditorHolder.set(auditor)
    }

    override fun afterCompletion(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        ex: Exception?
    ) {
        AuditorHolder.clear()
    }

    private inline fun <reified A : Annotation> HandlerMethod.getAnnotation(): A? {
        return AnnotatedElementUtils.findMergedAnnotation(method, A::class.java)
    }

    companion object {
        private const val ACCOUNT_UUID_HEADER_NAME = "X-ACCOUNT-UUID"
    }
}

