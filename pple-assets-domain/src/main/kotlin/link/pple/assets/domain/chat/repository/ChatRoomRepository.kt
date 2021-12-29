package link.pple.assets.domain.chat.repository

import com.querydsl.core.types.ExpressionUtils.and
import link.pple.assets.domain.chat.entity.ChatRoom
import link.pple.assets.domain.chat.entity.QChatRoom.chatRoom
import link.pple.assets.domain.chat.entity.QChatRoomConnection.chatRoomConnection
import link.pple.assets.infrastructure.util.eqFilterNull
import link.pple.assets.infrastructure.util.inFilterEmpty
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import java.util.*

/**
 * @Author Heli
 */
interface ChatRoomRepository : JpaRepository<ChatRoom, Long>, ChatRoomRepositoryCustom

interface ChatRoomRepositoryCustom {

    fun findAll(accountUuid: UUID?, status: List<ChatRoom.Status>?): List<ChatRoom>
}

class ChatRoomRepositoryImpl : QuerydslRepositorySupport(ChatRoom::class.java), ChatRoomRepositoryCustom {

    override fun findAll(accountUuid: UUID?, status: List<ChatRoom.Status>?): List<ChatRoom> {
        return from(chatRoom)
            .innerJoin(chatRoom, chatRoomConnection.chatRoom)
            .where(
                and(
                    chatRoomConnection.account.uuid.eqFilterNull(accountUuid),
                    chatRoom.status.inFilterEmpty(status)
                )
            )
            .fetch()
    }
}
