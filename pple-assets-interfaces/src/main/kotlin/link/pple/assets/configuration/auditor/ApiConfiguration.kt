package link.pple.assets.configuration.auditor

import link.pple.assets.domain.account.service.AccountQuery
import org.springframework.boot.convert.ApplicationConversionService
import org.springframework.context.annotation.Configuration
import org.springframework.format.FormatterRegistry
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.time.format.DateTimeFormatter

/**
 * @Author Heli
 */
@Configuration
class ApiConfiguration(
    private val accountQuery: AccountQuery
) : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(AuditorInterceptor(accountQuery))
            .addPathPatterns("/*/api/**").order(1)
    }

    override fun addFormatters(registry: FormatterRegistry) {
        val dateTimeFormatterRegistrar = DateTimeFormatterRegistrar()
        dateTimeFormatterRegistrar.setDateFormatter(DateTimeFormatter.ISO_LOCAL_DATE)
        dateTimeFormatterRegistrar.setDateTimeFormatter(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        dateTimeFormatterRegistrar.registerFormatters(registry)

        ApplicationConversionService.configure(registry)
    }
}
