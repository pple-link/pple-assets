package link.pple.assets.interfaces.api.term

import link.pple.assets.domain.term.service.TermQuery
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @Author Heli
 */
@Validated
@RestController
class TermRestController(
    private val termQuery: TermQuery
) {

    @GetMapping(
        value = ["/term/api/v1/term"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    fun getAll(): List<TermDto> {
        val terms = termQuery.getAll()

        return terms.map {
            it.toDto()
        }
    }
}
