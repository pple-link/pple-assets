package link.pple.assets.domain.chat.service

/**
 * @Author Heli
 */

data class ChatHistoryDefinition(
    val chatRoomUuid: String,
    val senderAccountUuid: String,
    val message: String
)

data class ChatRoomDefinition(
    val name: String
)

data class ChatRoomConnectionDefinition(
    val chatRoomUuid: String,
    val accountUuid: String
)
