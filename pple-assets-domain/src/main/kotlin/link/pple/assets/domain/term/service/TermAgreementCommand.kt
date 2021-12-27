package link.pple.assets.domain.term.service

import link.pple.assets.configuration.jpa.Jpa.PPLE_TRANSACTION_MANAGER
import link.pple.assets.domain.account.service.AccountQuery
import link.pple.assets.domain.term.entity.TermAgreement
import link.pple.assets.domain.term.repository.TermAgreementRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @Author Heli
 */
@Transactional(PPLE_TRANSACTION_MANAGER)
@Service
class TermAgreementCommand(
    private val termAgreementRepository: TermAgreementRepository,
    private val termQuery: TermQuery,
    private val accountQuery: AccountQuery
) {

    fun create(definition: TermAgreementDefinition): TermAgreement {

        val account = accountQuery.getById(definition.accountId)
        val term = termQuery.getById(definition.termId)

        val termAgreement = TermAgreement.create(
            term = term,
            account = account,
            agreement = definition.agreement
        )

        return termAgreementRepository.save(termAgreement)
    }
}
