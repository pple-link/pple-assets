package link.pple.assets.domain.chat.service

import link.pple.assets.configuration.jpa.Jpa.PPLE_TRANSACTION_MANAGER
import link.pple.assets.domain.account.service.AccountQuery
import link.pple.assets.domain.chat.entity.ChatHistory
import link.pple.assets.domain.chat.repository.ChatHistoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @Author Heli
 */
@Transactional(PPLE_TRANSACTION_MANAGER)
@Service
class ChatHistoryCommand(
    private val accountQuery: AccountQuery,
    private val chatRoomQuery: ChatRoomQuery,
    private val chatHistoryRepository: ChatHistoryRepository
) {

    fun create(definition: ChatHistoryDefinition): ChatHistory {

        val chatRoom = chatRoomQuery.getByUuid(definition.chatRoomUuid)
        val account = accountQuery.getByUuid(definition.senderAccountUuid)

        val chatHistory = ChatHistory.create(
            chatRoom = chatRoom,
            sender = account,
            message = definition.message
        )

        return chatHistoryRepository.save(chatHistory)
    }
}
