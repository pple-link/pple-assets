package link.pple.assets.domain.term.service

import link.pple.assets.domain.term.repository.TermAgreementRepository
import org.springframework.stereotype.Service

/**
 * @Author Heli
 */
@Service
class TermAgreementQuery(
    private val termAgreementRepository: TermAgreementRepository
) {

    fun getByAccountId(accountId: Long) {

    }
}
