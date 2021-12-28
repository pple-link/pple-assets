package link.pple.assets.domain.donation.repository

import com.querydsl.core.types.ExpressionUtils.and
import link.pple.assets.domain.donation.entity.DonationHistory
import link.pple.assets.domain.donation.entity.QDonationHistory.donationHistory
import link.pple.assets.infrastructure.util.eqFilterNull
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

/**
 * @Author Heli
 */
interface DonationHistoryRepository : JpaRepository<DonationHistory, Long>, DonationHistoryRepositoryCustom {

}

interface DonationHistoryRepositoryCustom {

    fun findAll(donorAccountId: Long?, donationId: Long?): List<DonationHistory>
}

class DonationHistoryRepositoryImpl : QuerydslRepositorySupport(DonationHistory::class.java),
    DonationHistoryRepositoryCustom {

    override fun findAll(donorAccountId: Long?, donationId: Long?): List<DonationHistory> {
        return from(donationHistory)
            .where(
                and(
                    donationHistory.donor.id.eqFilterNull(donorAccountId),
                    donationHistory.donation.id.eqFilterNull(donationId)
                )
            )
            .fetch()
    }

}

