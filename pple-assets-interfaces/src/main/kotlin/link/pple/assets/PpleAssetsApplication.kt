package link.pple.assets

import link.pple.assets.configuration.AppProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
@EnableConfigurationProperties(AppProperties::class)
class PpleAssetsApplication

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

    @GetMapping(
        value = ["/admin"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun admin() = "admin"

    @GetMapping(
        value = ["/manager"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun manager() = "manager"

    @GetMapping(
        value = ["/any"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun any() = "any"


}
