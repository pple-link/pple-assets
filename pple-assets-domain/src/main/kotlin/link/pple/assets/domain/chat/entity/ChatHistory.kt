package link.pple.assets.domain.chat.entity

import link.pple.assets.configuration.jpa.BaseEntity
import link.pple.assets.domain.account.entity.Account
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

    @JoinColumn(name = "senderAccountId", referencedColumnName = "id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val sender: Account,

    val message: String,

    @Enumerated(EnumType.STRING)
    var status: Status
) : BaseEntity() {

    enum class Status {
        ACTIVE, DELETE
    }

    companion object {

        fun create(
            chatRoom: ChatRoom,
            sender: Account,
            message: String
        ) = ChatHistory(
            chatRoom = chatRoom,
            sender = sender,
            message = message,
            status = Status.ACTIVE
        )
    }
}
