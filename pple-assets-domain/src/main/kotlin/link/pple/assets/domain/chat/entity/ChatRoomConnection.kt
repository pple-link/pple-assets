package link.pple.assets.domain.chat.entity

import link.pple.assets.configuration.jpa.BaseEntity
import link.pple.assets.domain.account.entity.Account
import javax.persistence.*

/**
 * @Author Heli
 */
@Entity
@Table(name = "chat_room_connections")
class ChatRoomConnection private constructor(

    @JoinColumn(name = "chatRoomId", referencedColumnName = "id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val chatRoom: ChatRoom,

    @JoinColumn(name = "accountId", referencedColumnName = "id", updatable = false)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    val account: Account,

    @Enumerated(EnumType.STRING)
    val status: Status
) : BaseEntity() {

    enum class Status {
        CONNECT, DISCONNECT
    }

    companion object {

        fun create(
            chatRoom: ChatRoom,
            account: Account
        ) = ChatRoomConnection(
            chatRoom = chatRoom,
            account = account,
            status = Status.CONNECT
        )
    }
}
