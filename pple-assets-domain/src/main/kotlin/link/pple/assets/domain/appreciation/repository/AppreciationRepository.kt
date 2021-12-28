package link.pple.assets.domain.appreciation.repository

import link.pple.assets.domain.appreciation.entity.Appreciation
import link.pple.assets.domain.appreciation.entity.QAppreciation.appreciation
import link.pple.assets.domain.donation.entity.Donation
import link.pple.assets.domain.donation.entity.QDonation.donation
import link.pple.assets.domain.donation.entity.QDonationHistory.donationHistory
import link.pple.assets.infrastructure.util.eqFilterNull
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import java.util.*

/**
 * @Author Heli
 */
interface AppreciationRepository : JpaRepository<Appreciation, Long>, AppreciationRepositoryCustom {
}

interface AppreciationRepositoryCustom {

    fun findByUuid(uuid: UUID): Appreciation?

    fun findAllByDonation(donation: Donation): List<Appreciation>

    fun findAll(donorAccountId: Long?, donationId: Long?): List<Appreciation>
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

    override fun findAllByDonation(donation: Donation): List<Appreciation> {
        return from(appreciation)
            .where(
                appreciation.status.eq(Appreciation.Status.ACTIVE)
            )
            .fetch()
    }

    override fun findAll(donorAccountId: Long?, donationId: Long?): List<Appreciation> {
        return from(appreciation)
            .join(appreciation.donation, donation)
            .join(donationHistory).on(donationHistory.donation.eq(donation))
            .where(
                appreciation.status.eq(Appreciation.Status.ACTIVE)
                    .and(appreciation.donation.id.eqFilterNull(donationId))
                    .and(donationHistory.donor.id.eqFilterNull(donorAccountId))
            )
            .fetch()
    }

}
