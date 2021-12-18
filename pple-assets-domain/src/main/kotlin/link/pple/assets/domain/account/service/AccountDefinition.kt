package link.pple.assets.domain.account.service

import link.pple.assets.domain.account.entity.Account

data class AccountDefinition(
    val key: Account.ProviderKey,
    val email: String,
    val displayName: String,
)
