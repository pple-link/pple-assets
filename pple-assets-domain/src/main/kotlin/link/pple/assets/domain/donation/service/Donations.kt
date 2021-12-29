package link.pple.assets.domain.donation.service

import link.pple.assets.domain.patient.service.PatientDefinition

data class DonationDefinition(
    val title: String,
    val content: String,
    val patient: PatientDefinition,
    val needCount: Long?
)
