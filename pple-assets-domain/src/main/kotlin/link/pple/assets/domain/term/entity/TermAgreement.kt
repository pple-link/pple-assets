package link.pple.assets.domain.term.entity

import link.pple.assets.configuration.jpa.BaseAuditingEntity
import link.pple.assets.domain.account.entity.Account
import org.hibernate.annotations.Where
import javax.persistence.*

/**
 * @Author Heli
 */
@Entity
@Table(name = "term_agreements")
class TermAgreement private constructor(

    @JoinColumn(name = "termId", referencedColumnName = "id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val term: Term,

    @JoinColumn(name = "accountId", referencedColumnName = "id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val account: Account,

    @Where(clause = "agreement != 'DISAGREE'")
    var agreement: Agreement

) : BaseAuditingEntity() {

    enum class Agreement {
        AGREE, DISAGREE
    }

    companion object {

        fun create(
            term: Term,
            account: Account,
            agreement: Agreement
        ) = TermAgreement(
            term = term,
            agreement = agreement,
            account = account
        )
    }
}
