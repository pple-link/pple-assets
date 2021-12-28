package link.pple.assets.domain.donation.service

import link.pple.assets.domain.donation.entity.DonationHistory
import link.pple.assets.domain.donation.repository.DonationHistoryRepository
import org.springframework.stereotype.Service

/**
 * @Author Heli
 */
@Service
class DonationHistoryQuery(
    private val donationHistoryRepository: DonationHistoryRepository
) {

    fun getAll(donorAccountId: Long?, donationId: Long?): List<DonationHistory> {
        return donationHistoryRepository.findAll(
            donorAccountId = donorAccountId,
            donationId = donationId
        )
    }
}
