package link.pple.assets.domain.donation.service

import link.pple.assets.configuration.jpa.Jpa
import link.pple.assets.domain.donation.entity.Donation
import link.pple.assets.domain.donation.repository.DonationRepository
import link.pple.assets.domain.patient.service.PatientCommand
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @Author Heli
 */
@Transactional(Jpa.PPLE_TRANSACTION_MANAGER)
@Service
class DonationCommand(
    private val donationRepository: DonationRepository,
    private val donationQuery: DonationQuery,
    private val patientCommand: PatientCommand
) {

    fun create(definition: DonationDefinition): Donation {

        // TODO Patient 재활용 가능하게 수정??
        val newPatient = patientCommand.create(definition = definition.patient)

        val donation = Donation.create(
            title = definition.title,
            content = definition.content,
            bloodProduct = definition.bloodProduct,
            patient = newPatient,
            phoneNumber = definition.phoneNumber
        )

        return donationRepository.save(donation)
    }

    fun renew(donationUuid: String): Donation {
        val donation = donationQuery.getByUuid(donationUuid)

        return donationRepository.save(donation.renew())
    }
}
