package link.pple.assets.domain.chat.service

import link.pple.assets.domain.chat.entity.ChatRoom
import link.pple.assets.domain.chat.repository.ChatRoomRepository
import link.pple.assets.infrastructure.util.toUUID
import link.pple.assets.util.notNull
import org.springframework.stereotype.Service

/**
 * @Author Heli
 */
@Service
class ChatRoomQuery(
    private val chatRoomRepository: ChatRoomRepository
) {

    fun getByUuid(uuid: String): ChatRoom {
        return chatRoomRepository.findByUuid(uuid.toUUID()).notNull { "ChatRoom 을 찾을 수 없음 [$uuid]" }
    }

    fun getAll(accountUuid: String?, status: List<ChatRoom.Status>?): List<ChatRoom> {
        return chatRoomRepository.findAll(
            accountUuid = accountUuid?.toUUID(),
            status = status
        )
    }
}
