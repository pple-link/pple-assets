package link.pple.assets.domain.chat.service

import link.pple.assets.domain.chat.entity.ChatHistory
import link.pple.assets.domain.chat.repository.ChatHistoryRepository
import link.pple.assets.infrastructure.util.toUUID
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

/**
 * @Author Heli
 */
@Service
class ChatHistoryQuery(
    private val chatHistoryRepository: ChatHistoryRepository
) {

    fun getExecutionPage(
        chatRoomUuid: String,
        status: List<ChatHistory.Status>?,
        pageable: Pageable
    ): Page<ChatHistory> {

        return chatHistoryRepository.findAll(
            chatRoomUuid = chatRoomUuid.toUUID(),
            status = status,
            pageable = pageable
        )
    }
}
