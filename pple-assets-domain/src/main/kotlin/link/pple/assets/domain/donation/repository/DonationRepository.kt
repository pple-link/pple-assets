package link.pple.assets.domain.donation.repository

import com.querydsl.core.QueryResults
import com.querydsl.core.types.ExpressionUtils.and
import link.pple.assets.configuration.jpa.Auditor
import link.pple.assets.domain.donation.entity.Donation
import link.pple.assets.domain.donation.entity.QDonation.donation
import link.pple.assets.domain.patient.entity.QPatient.patient
import link.pple.assets.infrastructure.util.eqFilterNull
import link.pple.assets.infrastructure.util.inFilterEmpty
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import java.util.*

/**
 * @Author Heli
 */
interface DonationRepository : JpaRepository<Donation, Long>, DonationRepositoryCustom {

}

interface DonationRepositoryCustom {

    fun load(uuid: UUID): Donation?
    fun load(status: List<Donation.Status>?, createdAuditor: Auditor?, pageable: Pageable): Page<Donation>
}

class DonationRepositoryImpl : QuerydslRepositorySupport(Donation::class.java), DonationRepositoryCustom {
    override fun load(uuid: UUID): Donation? {
        return from(donation)
            .join(donation.patient, patient).fetchJoin()
            .where(
                donation.uuid.eq(uuid)
            )
            .fetchOne()
    }

    override fun load(
        status: List<Donation.Status>?,
        createdAuditor: Auditor?,
        pageable: Pageable
    ): Page<Donation> {
        val results: QueryResults<Donation> = from(donation)
            .join(donation.patient, patient).fetchJoin()
            .where(
                and(
                    donation.createdBy.eqFilterNull(createdAuditor),
                    donation.status.inFilterEmpty(status)
                )
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(donation.createdAt.desc())
            .fetchResults()

        return PageImpl(results.results, pageable, results.total)
    }
}

