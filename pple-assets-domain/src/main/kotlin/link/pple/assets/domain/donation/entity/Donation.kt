package link.pple.assets.domain.donation.entity

import link.pple.assets.configuration.jpa.BaseAuditingEntity
import link.pple.assets.domain.patient.entity.Patient
import org.hibernate.annotations.Where
import javax.persistence.*

/**
 * @Author Heli
 */
@Entity
@Table(name = "donations")
class Donation private constructor(

    var title: String,

    var content: String,

    @JoinColumn(name = "patientId", referencedColumnName = "id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val patient: Patient,

    var needCount: Long?,

    @Where(clause = "status != 'DELETE'")
    @Enumerated(EnumType.STRING)
    var status: Status

) : BaseAuditingEntity() {

    val isUnlimitedNeedCount: Boolean
        get() = needCount == null

    enum class Status {
        ACTIVE, COMPLETE, DELETE
    }

    companion object {

        fun create(
            title: String,
            content: String,
            patient: Patient,
            needCount: Long?
        ) = Donation(
            title = title,
            content = content,
            patient = patient,
            needCount = needCount,
            status = Status.ACTIVE
        )
    }
}
