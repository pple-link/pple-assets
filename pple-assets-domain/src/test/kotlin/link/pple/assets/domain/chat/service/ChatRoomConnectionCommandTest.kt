package link.pple.assets.domain.chat.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import link.pple.assets.domain.account.entity.Account
import link.pple.assets.domain.account.service.AccountQuery
import link.pple.assets.domain.chat.entity.ChatRoom
import link.pple.assets.domain.chat.entity.ChatRoomConnection
import link.pple.assets.domain.chat.repository.ChatRoomConnectionRepository
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
internal class ChatRoomConnectionCommandTest {

    @MockK
    private lateinit var chatRoomQuery: ChatRoomQuery

    @MockK
    private lateinit var accountQuery: AccountQuery

    @MockK
    private lateinit var chatRoomConnectionRepository: ChatRoomConnectionRepository

    @InjectMockKs
    private lateinit var sut: ChatRoomConnectionCommand

    @Test
    fun `ChatRoomConnection 을 생성할 수 있다`() {
        // given
        val existChatRoom = mockk<ChatRoom>()
        val existAccount = mockk<Account>()
        val definition = ChatRoomConnectionDefinition(
            chatRoomUuid = EXIST_CHAT_ROOM_UUID.toString(),
            accountUuid = EXIST_ACCOUNT_UUID.toString(),
        )
        val chatRoomConnection = ChatRoomConnection.create(
            chatRoom = existChatRoom,
            account = existAccount
        )
        every { chatRoomQuery.getByUuid(definition.chatRoomUuid) } returns existChatRoom
        every { accountQuery.getByUuid(definition.accountUuid) } returns existAccount
        every { chatRoomConnectionRepository.save(any()) } returns chatRoomConnection

        // when
        val actual = sut.create(definition = definition)

        // then
        expectThat(actual) {
            get { chatRoom } isNotEqualTo null
            get { account } isNotEqualTo null
            get { status } isEqualTo ChatRoomConnection.Status.CONNECT
        }
    }

    companion object {
        private val EXIST_CHAT_ROOM_UUID = UUID.randomUUID()
        private val EXIST_ACCOUNT_UUID = UUID.randomUUID()
    }
}
