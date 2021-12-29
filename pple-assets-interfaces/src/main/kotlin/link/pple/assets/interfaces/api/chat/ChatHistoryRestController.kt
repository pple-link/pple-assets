package link.pple.assets.interfaces.api.chat

import link.pple.assets.configuration.auditor.AuditingApi
import link.pple.assets.domain.chat.entity.ChatHistory
import link.pple.assets.domain.chat.service.ChatHistoryCommand
import link.pple.assets.domain.chat.service.ChatHistoryQuery
import link.pple.assets.interfaces.api.PagedDto
import link.pple.assets.interfaces.api.pagedDto
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * @Author Heli
 */
@Validated
@RestController
class ChatHistoryRestController(
    private val chatHistoryQuery: ChatHistoryQuery,
    private val chatHistoryCommand: ChatHistoryCommand
) {

    @GetMapping(
        value = ["/chat/api/v1/chat/room/{chatRoomUuid}/history"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @AuditingApi
    fun getAllChatHistories(
        @PathVariable chatRoomUuid: String,
        @RequestParam(required = false) status: List<ChatHistory.Status>?,
        @PageableDefault pageable: Pageable
    ): PagedDto<ChatHistoryDto> {

        val chatHistories = chatHistoryQuery.getExecutionPage(
            chatRoomUuid = chatRoomUuid,
            status = status,
            pageable = pageable
        )

        return chatHistories.pagedDto { it.toDto() }
    }

    @PostMapping(
        value = ["/chat/api/v1/chat/history"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @AuditingApi
    fun createChatHistory(
        @RequestBody @Valid definitionDto: ChatHistoryDefinitionDto
    ): ChatHistoryDto {

        val definition = definitionDto.toDefinition()

        val chatHistory = chatHistoryCommand.create(definition = definition)

        return chatHistory.toDto()
    }
}
