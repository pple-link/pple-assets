package link.pple.assets

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class PpleAssetsApplication
//    (
//    private val smsClient: SmsClient
//) {
//
//    @PostConstruct
//    fun init() {
//        smsClient.sendOne()
//    }
//}

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
