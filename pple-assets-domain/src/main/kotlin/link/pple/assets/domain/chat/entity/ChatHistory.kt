package link.pple.assets.domain.chat.entity

import link.pple.assets.configuration.jpa.BaseAuditingEntity
import javax.persistence.*

/**
 * @Author Heli
 */
@Entity
@Table(name = "chat_histories")
class ChatHistory private constructor(

    @JoinColumn(name = "chatRoomId", referencedColumnName = "id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val chatRoom: ChatRoom,

    val message: String
) : BaseAuditingEntity() {
}
