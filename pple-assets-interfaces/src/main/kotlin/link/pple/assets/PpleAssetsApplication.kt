package link.pple.assets

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
class PpleAssetsApplication (){


    @Component
    class WebMvcConfig : WebMvcConfigurer {

        override fun addCorsMappings(registry: CorsRegistry) {
            registry.addMapping("/api/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS)
        }

        companion object {
            private const val MAX_AGE_SECS: Long = 3600
        }
    }
}

fun main(args: Array<String>) {

    runApplication<PpleAssetsApplication>(*args)

}


@RestController
class HelloRestController {

    @GetMapping(
        value = ["/hello"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun hello() = "Hello, pple-assets"
}
