package link.pple.assets.domain.donation.service

import link.pple.assets.domain.donation.entity.Donation
import link.pple.assets.domain.donation.repository.DonationRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

/**
 * @Author Heli
 */
@Service
class DonationQuery(
    private val donationRepository: DonationRepository
) {

    fun getExecutionPage(
        status: List<Donation.Status>?,
        pageable: Pageable
    ): Page<Donation> {
        return donationRepository.load(
            status = status,
            pageable = pageable
        )
    }
}
