package link.pple.assets.domain.chat.entity

import link.pple.assets.configuration.jpa.BaseAuditingEntity
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

/**
 * @Author Heli
 */
@Entity
@Table(name = "chat_rooms")
class ChatRoom private constructor(

    @Column(columnDefinition = "BINARY(16)")
    val uuid: UUID
) : BaseAuditingEntity() {
}
