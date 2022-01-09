package link.pple.assets.domain.donation.service

import link.pple.assets.configuration.jpa.Jpa
import link.pple.assets.domain.account.entity.Account
import link.pple.assets.domain.donation.entity.Donation
import link.pple.assets.domain.donation.entity.DonationHistory
import link.pple.assets.domain.donation.repository.DonationHistoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @Author Heli
 */
@Transactional(Jpa.PPLE_TRANSACTION_MANAGER)
@Service
class DonationHistoryCommand(
    private val donationHistoryRepository: DonationHistoryRepository
) {

    fun create(donation: Donation, donorAccount: Account): DonationHistory {

        val donationHistory = DonationHistory.create(
            donation = donation,
            donor = donorAccount
        )

        return donationHistoryRepository.save(donationHistory)
    }
}
