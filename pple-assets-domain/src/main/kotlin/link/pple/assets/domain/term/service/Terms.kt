package link.pple.assets.domain.term.service

import link.pple.assets.domain.term.entity.TermAgreement

data class TermAgreementDefinition(
    val termId: Long,
    val accountId: Long,
    val agreement: TermAgreement.Agreement
)
