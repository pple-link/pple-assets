package link.pple.assets.interfaces.api.chat

import link.pple.assets.domain.chat.entity.ChatHistory
import link.pple.assets.domain.chat.entity.ChatRoom
import link.pple.assets.domain.chat.entity.ChatRoomConnection
import link.pple.assets.domain.chat.service.ChatHistoryDefinition
import link.pple.assets.domain.chat.service.ChatRoomConnectionDefinition
import link.pple.assets.domain.chat.service.ChatRoomDefinition
import link.pple.assets.interfaces.api.AuditingEntityDto
import link.pple.assets.interfaces.api.EntityDto
import link.pple.assets.interfaces.api.entityData
import javax.validation.constraints.NotEmpty

/**
 * @Author Heli
 */

data class ChatRoomDefinitionDto(

    @NotEmpty
    val name: String
)

data class ChatHistoryDefinitionDto(

    @NotEmpty
    val chatRoomUuid: String,

    @NotEmpty
    val senderAccountUuid: String,

    @NotEmpty
    val message: String
)

data class ChatRoomConnectionDefinitionDto(

    @NotEmpty
    val chatRoomUuid: String,

    @NotEmpty
    val accountUuid: String
)

data class ChatRoomDto(
    val name: String,
    val status: String
) : AuditingEntityDto()

data class ChatHistoryDto(
    val chatRoomUuid: String,
    val message: String,
    val status: String
) : EntityDto()

data class ChatRoomConnectionDto(
    val chatRoomUuid: String,
    val accountUuid: String,
    val status: String
) : EntityDto()

// ===============================

internal fun ChatRoom.toDto() = ChatRoomDto(
    name = title,
    status = status.name
).entityData(this)

internal fun ChatHistory.toDto() = ChatHistoryDto(
    chatRoomUuid = uuid.toString(),
    message = message,
    status = status.name
).entityData(this)

internal fun ChatRoomConnection.toDto() = ChatRoomConnectionDto(
    chatRoomUuid = chatRoom.uuid.toString(),
    accountUuid = account.uuid.toString(),
    status = status.name
).entityData(this)


internal fun ChatRoomDefinitionDto.toDefinition() = ChatRoomDefinition(
    title = name
)

internal fun ChatHistoryDefinitionDto.toDefinition() = ChatHistoryDefinition(
    chatRoomUuid = chatRoomUuid,
    senderAccountUuid = senderAccountUuid,
    message = message
)

internal fun ChatRoomConnectionDefinitionDto.toDefinition() = ChatRoomConnectionDefinition(
    chatRoomUuid = chatRoomUuid,
    accountUuid = accountUuid
)
