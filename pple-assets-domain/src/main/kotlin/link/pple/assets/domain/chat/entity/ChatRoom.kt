package link.pple.assets.domain.chat.entity

import link.pple.assets.configuration.jpa.BaseAuditingEntity
import javax.persistence.Entity
import javax.persistence.Table

/**
 * @Author Heli
 */
@Entity
@Table(name = "chat_rooms")
class ChatRoom private constructor(

) : BaseAuditingEntity() {
}
