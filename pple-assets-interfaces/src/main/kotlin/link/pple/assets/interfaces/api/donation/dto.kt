package link.pple.assets.interfaces.api.donation

import link.pple.assets.domain.Blood
import link.pple.assets.domain.donation.entity.Donation
import link.pple.assets.domain.donation.entity.DonationHistory
import link.pple.assets.domain.donation.service.DonationDefinition
import link.pple.assets.domain.patient.entity.Patient
import link.pple.assets.domain.patient.service.PatientDefinition
import link.pple.assets.interfaces.api.AuditingEntityDto
import link.pple.assets.interfaces.api.EntityDto
import link.pple.assets.interfaces.api.entityData
import java.time.LocalDateTime

/**
 * @Author Heli
 */

data class DonationHistoryDto(
    val donationUuid: String,
    val donorAccountUuid: String,
    val step: String
) : EntityDto()

data class DonationDto(
    val title: String,
    val content: String,
    val bloodProduct: List<String>,
    val patient: PatientDto,
    val phoneNumber: String,
    val status: String,
    val lastRenewedAt: LocalDateTime?,
    val renewedCount: Long
) : AuditingEntityDto()

data class PatientDto(
    val blood: BloodDto,
    val status: String
) : AuditingEntityDto()

data class BloodDto(
    val rh: String,
    val abo: String
)

data class DonationDefinitionDto(
    val title: String,
    val content: String,
    val bloodProduct: List<Blood.Product>,
    val patient: PatientDefinitionDto,
    val phoneNumber: String
)

data class PatientDefinitionDto(
    val blood: Blood
)

// ===============================

internal fun DonationHistory.toDto() = DonationHistoryDto(
    donationUuid = donation.uuid.toString(),
    donorAccountUuid = donor.uuid.toString(),
    step = step.name
).entityData(this)

internal fun Donation.toDto() = DonationDto(
    title = title,
    content = content,
    bloodProduct = bloodProduct.map { it.name },
    patient = patient.toDto(),
    phoneNumber = phoneNumber,
    status = status.name,
    lastRenewedAt = lastRenewedAt,
    renewedCount = renewedCount.get()
).entityData(this)

internal fun Patient.toDto() = PatientDto(
    blood = BloodDto(
        rh = blood.rh.name,
        abo = blood.abo.name
    ),
    status = status.name
).entityData(this)

internal fun DonationDefinitionDto.toDefinition() = DonationDefinition(
    title = title,
    content = content,
    bloodProduct = bloodProduct,
    patient = PatientDefinition(
        blood = patient.blood
    ),
    phoneNumber = phoneNumber
)
