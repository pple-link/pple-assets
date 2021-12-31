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
import strikt.assertions.isEqualTo

/**
 * @Author Heli
 */
@ExtendWith(MockKExtension::class)
internal class ChatRoomCommandTest {

    @MockK
    private lateinit var chatRoomRepository: ChatRoomRepository

    @InjectMockKs
    private lateinit var sut: ChatRoomCommand

    @Test
    fun `ChatRoom 을 생성할 수 있다`() {
        // given
        val definition = ChatRoomDefinition(
            title = "chatRoom"
        )
        every { chatRoomRepository.save(any()) } returns ChatRoom.create(
            title = definition.title
        )

        // when
        val actual = sut.create(definition = definition)

        // then
        expectThat(actual) {
            get { title } isEqualTo "chatRoom"
        }
    }
}
