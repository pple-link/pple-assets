package link.pple.assets.domain.term.repository

import link.pple.assets.domain.term.entity.QTerm.term
import link.pple.assets.domain.term.entity.Term
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

/**
 * @Author Heli
 */
interface TermRepository : JpaRepository<Term, Long>, TermRepositoryCustom

interface TermRepositoryCustom {

    fun load(termId: Long): Term?

    fun load(): List<Term>
}

class TermRepositoryImpl : QuerydslRepositorySupport(Term::class.java), TermRepositoryCustom {

    override fun load(termId: Long): Term? {
        return from(term)
            .where(
                term.id.eq(termId)
            )
            .fetchOne()
    }

    override fun load(): List<Term> {
        return from(term)
            .where(
                term.status.eq(Term.Status.ACTIVE)
            )
            .orderBy(term.seq.asc())
            .fetch()
    }
}
