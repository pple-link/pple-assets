package link.pple.assets.domain.account.service

import link.pple.assets.domain.Blood
import link.pple.assets.domain.account.entity.Account
import java.time.LocalDate

data class AccountCreateDefinition(
    val key: Account.ProviderKey,
    val email: String,
    val displayName: String,
    val profileImageUrl: String?
)

data class AccountApplyDefinition(
    val uuid: String,
    val displayName: String,
    val birthDay: LocalDate,
    val gender: Account.Gender,
    val phoneNumber: String,
    val blood: Blood
)
