package link.pple.assets.domain.term.service

import link.pple.assets.domain.term.entity.Term
import link.pple.assets.domain.term.repository.TermRepository
import link.pple.assets.util.notNull
import org.springframework.stereotype.Service

/**
 * @Author Heli
 */
@Service
class TermQuery(
    private val termRepository: TermRepository
) {

    fun getById(termId: Long): Term {
        return termRepository.load(termId).notNull { "Term 를 찾을 수 없음 [$termId]" }
    }

    fun getAll(): List<Term> {
        return termRepository.load()
    }
}
