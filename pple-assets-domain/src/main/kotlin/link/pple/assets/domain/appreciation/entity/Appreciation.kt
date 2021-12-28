package link.pple.assets.domain.appreciation.entity

import link.pple.assets.configuration.jpa.BaseAuditingEntity
import link.pple.assets.domain.donation.entity.Donation
import org.hibernate.annotations.Where
import javax.persistence.*

/**
 * @Author Heli
 */
@Entity
@Table(name = "appreciations")
class Appreciation private constructor(

    val content: String,

    @JoinColumn(name = "donationId", referencedColumnName = "id", updatable = false)
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    val donation: Donation,

    @Where(clause = "status != 'DELETE'")
    @Enumerated(EnumType.STRING)
    val status: Status

) : BaseAuditingEntity() {

    enum class Status {
        ACTIVE, DELETE
    }

    companion object {

        fun create(
            content: String,
            donation: Donation
        ) = Appreciation(
            content = content,
            donation = donation,
            status = Status.ACTIVE
        )
    }
}
