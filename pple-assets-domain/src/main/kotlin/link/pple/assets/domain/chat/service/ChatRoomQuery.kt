package link.pple.assets.domain.chat.service

import link.pple.assets.domain.chat.entity.ChatRoom
import link.pple.assets.domain.chat.repository.ChatRoomRepository
import link.pple.assets.infrastructure.util.toUUID
import org.springframework.stereotype.Service

/**
 * @Author Heli
 */
@Service
class ChatRoomQuery(
    private val chatRoomRepository: ChatRoomRepository
) {

    fun getAll(accountUuid: String?, status: List<ChatRoom.Status>?): List<ChatRoom> {
        return chatRoomRepository.findAll(
            accountUuid = accountUuid?.toUUID(),
            status = status
        )
    }
}
