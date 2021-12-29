package link.pple.assets.domain.appreciation.repository

import com.querydsl.core.types.ExpressionUtils.and
import link.pple.assets.domain.appreciation.entity.Appreciation
import link.pple.assets.domain.appreciation.entity.QAppreciation.appreciation
import link.pple.assets.domain.donation.entity.QDonation.donation
import link.pple.assets.domain.donation.entity.QDonationHistory.donationHistory
import link.pple.assets.infrastructure.util.eqFilterNull
import link.pple.assets.infrastructure.util.inFilterEmpty
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import java.util.*

/**
 * @Author Heli
 */
interface AppreciationRepository : JpaRepository<Appreciation, Long>, AppreciationRepositoryCustom

interface AppreciationRepositoryCustom {

    fun findByUuid(uuid: UUID): Appreciation?

    fun findAll(donorAccountUuid: UUID?, donationUuid: UUID?, status: List<Appreciation.Status>?): List<Appreciation>
}

class AppreciationRepositoryImpl : QuerydslRepositorySupport(Appreciation::class.java),
    AppreciationRepositoryCustom {

    override fun findByUuid(uuid: UUID): Appreciation? {
        return from(appreciation)
            .where(
                appreciation.uuid.eq(uuid)
                    .and(appreciation.status.eq(Appreciation.Status.ACTIVE))

            )
            .fetchOne()
    }

    override fun findAll(
        donorAccountUuid: UUID?,
        donationUuid: UUID?,
        status: List<Appreciation.Status>?
    ): List<Appreciation> {
        return from(appreciation)
            .join(appreciation.donation, donation)
            .join(donationHistory).on(donationHistory.donation.eq(donation))
            .where(
                and(
                    and(
                        appreciation.status.inFilterEmpty(status),
                        appreciation.donation.uuid.eqFilterNull(donationUuid)
                    ),
                    donationHistory.donor.uuid.eqFilterNull(donorAccountUuid)
                )
            )
            .fetch()
    }
}
