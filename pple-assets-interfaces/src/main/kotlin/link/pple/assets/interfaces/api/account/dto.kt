package link.pple.assets.interfaces.api.account

import link.pple.assets.domain.Blood
import link.pple.assets.domain.account.entity.Account
import link.pple.assets.domain.account.service.AccountDefinition
import link.pple.assets.interfaces.api.EntityDto
import link.pple.assets.interfaces.api.entityData
import java.time.LocalDate
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

data class AccountDefinitionDto(

    @NotNull
    @Valid
    val key: ProviderKeyDto,

    @Email
    val email: String,

    @NotEmpty
    val displayName: String,

    @NotNull
    val birthDay: LocalDate,

    @NotEmpty
    val gender: String,

    @NotEmpty
    val phoneNumber: String,

    @NotEmpty
    val profileImageUrl: String,

    @NotNull
    @Valid
    val blood: BloodDto
)

data class AccountPatchDto(

    @NotEmpty
    val displayName: String,

    val profileImageUrl: String?
)

// ===============================

data class AccountDto(
    val key: ProviderKeyDto,
    val email: String,
    val displayName: String,
    val birthDay: LocalDate,
    val gender: String,
    val phoneNumber: String,
    val profileImageUrl: String?,
    val blood: BloodDto,
    val role: String,
    val status: String
) : EntityDto()

data class ProviderKeyDto(

    @NotNull
    val type: String,

    @NotNull
    val id: String
)

data class BloodDto(

    @NotEmpty
    val abo: String,

    @NotEmpty
    val rh: String
)

// ===============================

internal fun Account.toDto() = AccountDto(
    key = key.toDto(),
    email = email,
    displayName = displayName,
    birthDay = birthDay,
    gender = gender.name,
    phoneNumber = phoneNumber,
    profileImageUrl = profileImageUrl,
    blood = blood.toDto(),
    role = role.name,
    status = status.name
).entityData(this)

internal fun Account.ProviderKey.toDto() = ProviderKeyDto(
    type = type.name,
    id = id
)

internal fun Blood.toDto() = BloodDto(
    abo = abo.name,
    rh = rh.name
)

internal fun AccountDefinitionDto.toDefinition() = AccountDefinition(
    key = Account.ProviderKey(
        id = key.id,
        type = Account.ProviderType.from(key.type)
    ),
    email = email,
    displayName = displayName,
    birthDay = birthDay,
    gender = Account.Gender.from(gender),
    phoneNumber = phoneNumber,
    profileImageUrl = profileImageUrl,
    blood = Blood(
        abo = Blood.ABO.from(blood.abo),
        rh = Blood.RH.from(blood.rh),
    )
)
