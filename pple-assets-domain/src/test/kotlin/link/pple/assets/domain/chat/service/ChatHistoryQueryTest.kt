package link.pple.assets.domain.chat.service

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import link.pple.assets.domain.chat.repository.ChatHistoryRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

/**
 * @Author Heli
 */
@ExtendWith(MockKExtension::class)
internal class ChatHistoryQueryTest {

    @MockK
    private lateinit var chatHistoryRepository: ChatHistoryRepository

    @InjectMockKs
    private lateinit var sut: ChatHistoryQuery

    @Test
    fun `ChatHistory 목록을 `() {
        // given


        // when


        // then

    }
}
