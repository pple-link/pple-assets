package link.pple.assets.domain.donation.repository

import com.querydsl.core.types.ExpressionUtils.and
import link.pple.assets.domain.donation.entity.DonationHistory
import link.pple.assets.domain.donation.entity.QDonationHistory.donationHistory
import link.pple.assets.infrastructure.util.eqFilterNull
import link.pple.assets.infrastructure.util.inFilterEmpty
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import java.util.*

/**
 * @Author Heli
 */
interface DonationHistoryRepository : JpaRepository<DonationHistory, Long>, DonationHistoryRepositoryCustom {

}

interface DonationHistoryRepositoryCustom {

    fun findAll(donorAccountUuid: UUID?, donationUuid: UUID?, steps: List<DonationHistory.Step>?): List<DonationHistory>
}

class DonationHistoryRepositoryImpl : QuerydslRepositorySupport(DonationHistory::class.java),
    DonationHistoryRepositoryCustom {

    override fun findAll(
        donorAccountUuid: UUID?,
        donationUuid: UUID?,
        steps: List<DonationHistory.Step>?
    ): List<DonationHistory> {
        return from(donationHistory)
            .where(
                and(
                    donationHistory.step.inFilterEmpty(steps),
                    and(
                        donationHistory.donor.uuid.eqFilterNull(donorAccountUuid),
                        donationHistory.donation.uuid.eqFilterNull(donationUuid)
                    )
                )
            )
            .fetch()
    }

}

