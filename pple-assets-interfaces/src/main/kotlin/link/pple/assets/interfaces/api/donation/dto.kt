package link.pple.assets.interfaces.api.donation

import link.pple.assets.configuration.jpa.requiredId
import link.pple.assets.domain.donation.entity.DonationHistory
import link.pple.assets.interfaces.api.EntityDto
import link.pple.assets.interfaces.api.entityData

/**
 * @Author Heli
 */

data class DonationHistoryDto(
    val donationId: Long,
    val donorAccountId: Long,
    val step: String
) : EntityDto()

// ===============================

internal fun DonationHistory.toDto() = DonationHistoryDto(
    donationId = donation.requiredId,
    donorAccountId = donor.requiredId,
    step = step.name
).entityData(this)
