package link.pple.assets.interfaces.api.chat

import link.pple.assets.configuration.auditor.AuditingApi
import link.pple.assets.domain.chat.entity.ChatRoom
import link.pple.assets.domain.chat.service.ChatRoomCommand
import link.pple.assets.domain.chat.service.ChatRoomQuery
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * @Author Heli
 */
@Validated
@RestController
class ChatRoomRestController(
    private val chatRoomQuery: ChatRoomQuery,
    private val chatRoomCommand: ChatRoomCommand
) {

    @GetMapping(
        value = ["/chat/api/v1/chat/room"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @AuditingApi
    fun getAllChatRooms(
        @RequestParam(required = false) accountUuid: String?,
        @RequestParam(required = false) status: List<ChatRoom.Status>?
    ): List<ChatRoomDto> {

        val chatRooms = chatRoomQuery.getAll(
            accountUuid = accountUuid,
            status = status
        )

        return chatRooms.map {
            it.toDto()
        }
    }

    @PostMapping(
        value = ["/chat/api/v1/chat/room"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE],
    )
    @AuditingApi
    fun createChatRoom(
        @RequestBody @Valid definitionDto: ChatRoomDefinitionDto
    ): ChatRoomDto {

        val definition = definitionDto.toDefinition()

        val chatRoom = chatRoomCommand.create(definition = definition)

        return chatRoom.toDto()
    }
}
