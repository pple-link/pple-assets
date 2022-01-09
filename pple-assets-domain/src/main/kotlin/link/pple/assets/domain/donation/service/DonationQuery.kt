package link.pple.assets.domain.donation.service

import link.pple.assets.domain.donation.entity.Donation
import link.pple.assets.domain.donation.repository.DonationRepository
import link.pple.assets.infrastructure.util.toUUID
import link.pple.assets.util.notNull
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

    fun getByUuid(donationUuid: String): Donation {
        return donationRepository.load(donationUuid.toUUID())
            .notNull { "Donation 을 찾을 수 없음 [$donationUuid]" }
    }

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
