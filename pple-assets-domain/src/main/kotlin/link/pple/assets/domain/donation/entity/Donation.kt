package link.pple.assets.domain.donation.entity

import link.pple.assets.configuration.jpa.BaseAuditingEntity
import link.pple.assets.domain.Blood
import link.pple.assets.domain.patient.entity.Patient
import link.pple.assets.util.notNull
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicLong
import javax.persistence.*

/**
 * @Author Heli
 */
@Entity
@Table(name = "donations")
class Donation private constructor(

    var title: String,

    var content: String,

    @Convert(converter = BloodProductConverter::class)
    var bloodProduct: List<Blood.Product>,

    @JoinColumn(name = "patientId", referencedColumnName = "id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val patient: Patient,

    var phoneNumber: String,

    @Where(clause = "status != 'DELETE'")
    @Enumerated(EnumType.STRING)
    var status: Status,

    var lastRenewedAt: LocalDateTime? = null,

    @Convert(converter = AtomicLongConverter::class)
    var renewedCount: AtomicLong = AtomicLong(0)

) : BaseAuditingEntity() {

    enum class Status {
        ACTIVE, COMPLETE, DELETE;

        companion object {
            private val items = values().associateBy { it.name.lowercase() }
            fun from(status: String): Status {
                return items[status.lowercase()].notNull { "Donation.Status can not parse [$status]" }
            }
        }
    }

    fun renew(): Donation {
        return this.apply {
            lastRenewedAt = LocalDateTime.now()
            renewedCount.addAndGet(1)
        }
    }

    companion object {

        fun create(
            title: String,
            content: String,
            bloodProduct: List<Blood.Product>,
            patient: Patient,
            phoneNumber: String
        ) = Donation(
            title = title,
            content = content,
            bloodProduct = bloodProduct,
            patient = patient,
            phoneNumber = phoneNumber,
            status = Status.ACTIVE
        )
    }
}
