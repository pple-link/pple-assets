package link.pple.assets.interfaces.api.term

import link.pple.assets.domain.term.entity.Term
import link.pple.assets.domain.term.service.TermQuery
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
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
    fun getAllTerms(
        @RequestParam(required = false) status: List<Term.Status>?
    ): List<TermDto> {
        val terms = termQuery.getAll(
            status = status
        )

        return terms.map {
            it.toDto()
        }
    }
}
