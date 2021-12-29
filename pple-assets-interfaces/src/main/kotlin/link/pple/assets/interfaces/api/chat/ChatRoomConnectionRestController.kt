package link.pple.assets.interfaces.api.chat

import link.pple.assets.configuration.auditor.AuditingApi
import link.pple.assets.domain.chat.service.ChatRoomConnectionCommand
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

/**
 * @Author Heli
 */
@Validated
@RestController
class ChatRoomConnectionRestController(
    private val chatRoomConnectionCommand: ChatRoomConnectionCommand
) {

    @PostMapping(
        value = ["/chat/api/v1/chat/room/connection"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @AuditingApi
    fun createChatRoomConnection(
        @RequestBody @Valid definitionDto: ChatRoomConnectionDefinitionDto
    ): ChatRoomConnectionDto {

        val definition = definitionDto.toDefinition()

        val chatRoomConnection = chatRoomConnectionCommand.create(
            definition = definition
        )

        return chatRoomConnection.toDto()
    }
}
