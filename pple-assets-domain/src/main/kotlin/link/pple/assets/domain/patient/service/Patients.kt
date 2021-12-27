package link.pple.assets.domain.patient.service

import link.pple.assets.domain.Blood
import java.time.LocalDate

data class PatientDefinition(
    val name: String,
    val birthDay: LocalDate,
    val blood: Blood,
    val registrationIdentifier: String,
    val medicalFacilityName: String,
    val medicalFacilityRoom: String
)
