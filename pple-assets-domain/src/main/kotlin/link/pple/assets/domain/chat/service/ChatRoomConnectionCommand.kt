package link.pple.assets.domain.chat.service

import link.pple.assets.configuration.jpa.Jpa.PPLE_TRANSACTION_MANAGER
import link.pple.assets.domain.account.service.AccountQuery
import link.pple.assets.domain.chat.entity.ChatRoomConnection
import link.pple.assets.domain.chat.repository.ChatRoomConnectionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @Author Heli
 */
@Transactional(PPLE_TRANSACTION_MANAGER)
@Service
class ChatRoomConnectionCommand(
    private val chatRoomQuery: ChatRoomQuery,
    private val accountQuery: AccountQuery,
    private val chatRoomConnectionRepository: ChatRoomConnectionRepository
) {

    fun create(definition: ChatRoomConnectionDefinition): ChatRoomConnection {

        val chatRoom = chatRoomQuery.getByUuid(definition.chatRoomUuid)
        val account = accountQuery.getByUuid(definition.accountUuid)

        val chatRoomConnection = ChatRoomConnection.create(
            chatRoom = chatRoom,
            account = account
        )

        return chatRoomConnectionRepository.save(chatRoomConnection)
    }
}
