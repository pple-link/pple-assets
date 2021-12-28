package link.pple.assets.domain.appreciation.service

import link.pple.assets.domain.appreciation.entity.Appreciation
import link.pple.assets.domain.appreciation.repository.AppreciationRepository
import link.pple.assets.domain.donation.service.DonationQuery
import link.pple.assets.util.notNull
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

/**
 * @Author Heli
 */
@Service
class AppreciationQuery(
    private val appreciationRepository: AppreciationRepository,
    private val donationQuery: DonationQuery
) {

    fun getById(appreciationId: Long): Appreciation {
        return appreciationRepository.findByIdOrNull(appreciationId)
            .notNull { "Appreciation 를 찾을 수 없음 [$appreciationId]" }
    }

    fun getByDonationId(donationId: Long): List<Appreciation> {
        val existDonation = donationQuery.getById(donationId)

        return appreciationRepository.findAllByDonation(existDonation)
    }

    fun getByUuid(uuid: String): Appreciation {
        return appreciationRepository.findByUuid(UUID.fromString(uuid)).notNull { "Appreciation 를 찾을 수 없음 [$uuid]" }
    }

    fun getAll(donorAccountId: Long?, donationId: Long?): List<Appreciation> {
        return appreciationRepository.findAll(
            donorAccountId = donorAccountId,
            donationId = donationId
        )
    }
}
