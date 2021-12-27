package link.pple.assets.domain.patient.service

import link.pple.assets.configuration.jpa.Jpa.PPLE_TRANSACTION_MANAGER
import link.pple.assets.domain.patient.entity.Patient
import link.pple.assets.domain.patient.repository.PatientRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @Author Heli
 */
@Transactional(PPLE_TRANSACTION_MANAGER)
@Service
class PatientCommand(
    private val patientRepository: PatientRepository
) {

    fun create(definition: PatientDefinition): Patient {

        val patient = Patient.create(
            name = definition.name,
            birthDay = definition.birthDay,
            blood = definition.blood,
            registrationIdentifier = definition.registrationIdentifier,
            medicalFacilityName = definition.medicalFacilityName,
            medicalFacilityRoom = definition.medicalFacilityRoom
        )

        return patientRepository.save(patient)
    }
}
