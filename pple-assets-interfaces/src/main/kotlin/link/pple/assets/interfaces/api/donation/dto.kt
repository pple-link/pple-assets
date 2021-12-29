package link.pple.assets.interfaces.api.donation

import link.pple.assets.domain.donation.entity.DonationHistory
import link.pple.assets.interfaces.api.EntityDto
import link.pple.assets.interfaces.api.entityData

/**
 * @Author Heli
 */

data class DonationHistoryDto(
    val donationUuid: String,
    val donorAccountUuid: String,
    val step: String
) : EntityDto()

// ===============================

internal fun DonationHistory.toDto() = DonationHistoryDto(
    donationUuid = donation.uuid.toString(),
    donorAccountUuid = donor.uuid.toString(),
    step = step.name
).entityData(this)
