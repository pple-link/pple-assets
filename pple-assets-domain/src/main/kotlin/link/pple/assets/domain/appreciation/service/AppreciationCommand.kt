package link.pple.assets.domain.appreciation.service

import link.pple.assets.configuration.jpa.Jpa.PPLE_TRANSACTION_MANAGER
import link.pple.assets.domain.appreciation.entity.Appreciation
import link.pple.assets.domain.appreciation.repository.AppreciationRepository
import link.pple.assets.domain.donation.service.DonationQuery
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @Author Heli
 */
@Transactional(PPLE_TRANSACTION_MANAGER)
@Service
class AppreciationCommand(
    private val appreciationRepository: AppreciationRepository,
    private val donationQuery: DonationQuery
) {

    fun create(definition: AppreciationDefinition): Appreciation {

        val existDonation = donationQuery.getByUuid(definition.donationUuid)

        val appreciation = Appreciation.create(
            content = definition.content,
            donation = existDonation
        )

        return appreciationRepository.save(appreciation)
    }
}
