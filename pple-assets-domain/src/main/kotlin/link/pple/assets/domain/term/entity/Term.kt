package link.pple.assets.domain.term.entity

import link.pple.assets.configuration.jpa.BaseEntity
import org.hibernate.annotations.Where
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

/**
 * @Author Heli
 */
@Entity
@Table(name = "terms")
class Term private constructor(

    val title: String,

    val content: String,

    val required: Boolean,

    val seq: Long,

    @Where(clause = "status != 'INACTIVE'")
    @Enumerated(EnumType.STRING)
    val status: Status

) : BaseEntity() {

    enum class Status {
        ACTIVE, INACTIVE
    }

    companion object {

        fun create(
            title: String,
            content: String,
            required: Boolean,
            seq: Long
        ) = Term(
            title = title,
            content = content,
            required = required,
            seq = seq,
            status = Status.ACTIVE
        )
    }
}
