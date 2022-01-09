package link.pple.assets.domain.patient.entity

import link.pple.assets.configuration.jpa.BaseAuditingEntity
import link.pple.assets.domain.Blood
import org.hibernate.annotations.Where
import javax.persistence.*

/**
 * @Author Heli
 */
@Entity
@Table(name = "patients")
class Patient(

    @Embedded
    val blood: Blood,

    @Where(clause = "status != 'DELETE'")
    @Enumerated(EnumType.STRING)
    val status: Status

) : BaseAuditingEntity() {

    enum class Status {
        ACTIVE, DELETE
    }

    companion object {

        fun create(
            blood: Blood
        ) = Patient(
            blood = blood,
            status = Status.ACTIVE
        )
    }
}
