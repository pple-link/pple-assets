package link.pple.assets.domain.chat.service

import link.pple.assets.configuration.jpa.Jpa.PPLE_TRANSACTION_MANAGER
import link.pple.assets.domain.chat.entity.ChatRoom
import link.pple.assets.domain.chat.repository.ChatRoomRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @Author Heli
 */
@Transactional(PPLE_TRANSACTION_MANAGER)
@Service
class ChatRoomCommand(
    private val chatRoomRepository: ChatRoomRepository
) {

    fun create(definition: ChatRoomDefinition): ChatRoom {

        val chatRoom = ChatRoom.create(
            name = definition.name
        )

        return chatRoomRepository.save(chatRoom)
    }
}
