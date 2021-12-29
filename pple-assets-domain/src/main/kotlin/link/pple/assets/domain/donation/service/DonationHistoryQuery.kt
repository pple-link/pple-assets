package link.pple.assets.domain.donation.service

import link.pple.assets.domain.donation.entity.DonationHistory
import link.pple.assets.domain.donation.repository.DonationHistoryRepository
import link.pple.assets.infrastructure.util.toUUID
import org.springframework.stereotype.Service

/**
 * @Author Heli
 */
@Service
class DonationHistoryQuery(
    private val donationHistoryRepository: DonationHistoryRepository
) {

    fun getAll(
        donorAccountUuid: String?,
        donationUuid: String?,
        steps: List<DonationHistory.Step>?
    ): List<DonationHistory> {
        return donationHistoryRepository.findAll(
            donorAccountUuid = donorAccountUuid?.toUUID(),
            donationUuid = donationUuid?.toUUID(),
            steps = steps
        )
    }
}
