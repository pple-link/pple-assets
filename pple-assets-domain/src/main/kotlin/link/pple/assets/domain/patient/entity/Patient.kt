package link.pple.assets.domain.patient.entity

import link.pple.assets.configuration.jpa.BaseAuditingEntity
import link.pple.assets.domain.Blood
import org.hibernate.annotations.Where
import java.time.LocalDate
import javax.persistence.*

/**
 * @Author Heli
 */
@Entity
@Table(name = "patients")
class Patient(

    val name: String,

    val birthDay: LocalDate,

    @Embedded
    val blood: Blood,

    val registrationIdentifier: String,

    val medicalFacilityName: String,

    val medicalFacilityRoom: String,

    @Where(clause = "status != 'DELETE'")
    @Enumerated(EnumType.STRING)
    val status: Status

) : BaseAuditingEntity() {

    enum class Status {
        ACTIVE, DELETE
    }

    companion object {

        fun create(
            name: String,
            birthDay: LocalDate,
            blood: Blood,
            registrationIdentifier: String,
            medicalFacilityName: String,
            medicalFacilityRoom: String
        ) = Patient(
            name = name,
            birthDay = birthDay,
            blood = blood,
            registrationIdentifier = registrationIdentifier,
            medicalFacilityName = medicalFacilityName,
            medicalFacilityRoom = medicalFacilityRoom,
            status = Status.ACTIVE
        )
    }
}
