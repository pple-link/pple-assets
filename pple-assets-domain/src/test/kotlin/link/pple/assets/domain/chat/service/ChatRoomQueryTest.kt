package link.pple.assets.domain.chat.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import link.pple.assets.domain.chat.entity.ChatRoom
import link.pple.assets.domain.chat.repository.ChatRoomRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expectThat
import strikt.assertions.get
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import java.util.*

/**
 * @Author Heli
 */
@ExtendWith(MockKExtension::class)
internal class ChatRoomQueryTest {

    @MockK
    private lateinit var chatRoomRepository: ChatRoomRepository

    @InjectMockKs
    private lateinit var sut: ChatRoomQuery

    @Test
    fun `UUID 로 ChatRoom 을 조회할 수 있다`() {
        // given
        val chatRoom = ChatRoom.create(
            title = "name"
        )
        every { chatRoomRepository.findByUuid(EXIST_CHATROOM_UUID) } returns chatRoom

        // when
        val actual = sut.getByUuid(EXIST_CHATROOM_UUID.toString())

        // then
        expectThat(actual) {
            get { title } isEqualTo "name"
        }
    }

    @Test
    fun `ChatRoom 을 목록으로 조회할 수 있다`() {
        // given
        val firstChatRoom = ChatRoom.create(title = "first")
        val secondChatRoom = ChatRoom.create(title = "second")
        every { chatRoomRepository.findAll(EXIST_ACCOUNT_UUID, emptyList()) } returns listOf(
            firstChatRoom, secondChatRoom
        )

        // when
        val actual = sut.getAll(
            accountUuid = EXIST_ACCOUNT_UUID.toString(),
            status = emptyList()
        )

        // then
        expectThat(actual) {
            hasSize(2).and {
                get(0).and {
                    get { title } isEqualTo "first"
                }
                get(1).and {
                    get { title } isEqualTo "second"
                }
            }
        }
    }

    companion object {

        private val EXIST_CHATROOM_UUID = UUID.randomUUID()
        private val EXIST_ACCOUNT_UUID = UUID.randomUUID()
    }
}
