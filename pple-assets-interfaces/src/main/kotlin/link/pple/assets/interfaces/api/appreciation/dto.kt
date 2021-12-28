package link.pple.assets.interfaces.api.appreciation

import link.pple.assets.domain.appreciation.entity.Appreciation
import link.pple.assets.domain.appreciation.service.AppreciationDefinition
import link.pple.assets.interfaces.api.AuditingEntityDto
import link.pple.assets.interfaces.api.entityData
import javax.validation.constraints.NotEmpty

/**
 * @Author Heli
 */

data class AppreciationDefinitionDto(

    @NotEmpty
    val content: String,

    @NotEmpty
    val donationUuid: String
)

data class AppreciationDto(

    @NotEmpty
    val content: String,

    @NotEmpty
    val status: String
) : AuditingEntityDto()

// ===============================

internal fun Appreciation.toDto() = AppreciationDto(
    content = content,
    status = status.name
).entityData(this)

internal fun AppreciationDefinitionDto.toDefinition() = AppreciationDefinition(
    content = content,
    donationUuid = donationUuid
)
