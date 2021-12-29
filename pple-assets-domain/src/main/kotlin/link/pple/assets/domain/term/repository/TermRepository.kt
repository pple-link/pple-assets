package link.pple.assets.domain.term.repository

import link.pple.assets.domain.term.entity.QTerm.term
import link.pple.assets.domain.term.entity.Term
import link.pple.assets.infrastructure.util.inFilterEmpty
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

/**
 * @Author Heli
 */
interface TermRepository : JpaRepository<Term, Long>, TermRepositoryCustom

interface TermRepositoryCustom {

    fun load(termId: Long): Term?

    fun load(status: List<Term.Status>?): List<Term>
}

class TermRepositoryImpl : QuerydslRepositorySupport(Term::class.java), TermRepositoryCustom {

    override fun load(termId: Long): Term? {
        return from(term)
            .where(
                term.id.eq(termId)
            )
            .fetchOne()
    }

    override fun load(status: List<Term.Status>?): List<Term> {
        return from(term)
            .where(
                term.status.inFilterEmpty(status)
            )
            .orderBy(term.seq.asc())
            .fetch()
    }
}
