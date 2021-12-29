package link.pple.assets.domain.chat.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import link.pple.assets.domain.account.entity.Account
import link.pple.assets.domain.account.service.AccountQuery
import link.pple.assets.domain.chat.entity.ChatHistory
import link.pple.assets.domain.chat.entity.ChatRoom
import link.pple.assets.domain.chat.repository.ChatHistoryRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEqualTo
import java.util.*

/**
 * @Author Heli
 */
@ExtendWith(MockKExtension::class)
internal class ChatHistoryCommandTest {

    @MockK
    private lateinit var accountQuery: AccountQuery

    @MockK
    private lateinit var chatRoomQuery: ChatRoomQuery

    @MockK
    private lateinit var chatHistoryRepository: ChatHistoryRepository

    @InjectMockKs
    private lateinit var sut: ChatHistoryCommand


    @Test
    fun `ChatHistory 를 남길 수 있다`() {
        // given
        val chatHistoryDefinition = ChatHistoryDefinition(
            chatRoomUuid = UUID.randomUUID().toString(),
            senderAccountUuid = UUID.randomUUID().toString(),
            message = "message"
        )
        val existChatRoom = mockk<ChatRoom>()
        val existAccount = mockk<Account>()
        val chatHistory = ChatHistory.create(
            chatRoom = existChatRoom,
            sender = existAccount,
            message = chatHistoryDefinition.message
        )
        every { chatRoomQuery.getByUuid(chatHistoryDefinition.chatRoomUuid) } returns existChatRoom
        every { accountQuery.getByUuid(chatHistoryDefinition.senderAccountUuid) } returns existAccount
        every { chatHistoryRepository.save(any()) } returns chatHistory

        // when
        val actual = sut.create(definition = chatHistoryDefinition)

        // then
        expectThat(actual) {
            get { chatRoom } isNotEqualTo null
            get { sender } isNotEqualTo null
            get { message } isEqualTo "message"
            get { status } isEqualTo ChatHistory.Status.ACTIVE
        }
    }

}
