package link.pple.assets.domain.term.repository

import link.pple.assets.domain.term.entity.TermAgreement
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

/**
 * @Author Heli
 */
interface TermAgreementRepository : JpaRepository<TermAgreement, Long>

interface TermAgreementRepositoryCustom {
}

class TermAgreementRepositoryImpl : QuerydslRepositorySupport(TermAgreement::class.java),
    TermAgreementRepositoryCustom {

}
