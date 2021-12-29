package link.pple.assets.domain.chat.repository

import com.querydsl.core.QueryResults
import link.pple.assets.domain.chat.entity.ChatHistory
import link.pple.assets.domain.chat.entity.QChatHistory.chatHistory
import link.pple.assets.domain.chat.entity.QChatRoom.chatRoom
import link.pple.assets.infrastructure.util.inFilterEmpty
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import java.util.*

/**
 * @Author Heli
 */
interface ChatHistoryRepository : JpaRepository<ChatHistory, Long>, ChatHistoryRepositoryCustom

interface ChatHistoryRepositoryCustom {

    fun findAll(chatRoomUuid: UUID, status: List<ChatHistory.Status>?, pageable: Pageable): Page<ChatHistory>
}

class ChatHistoryRepositoryImpl : QuerydslRepositorySupport(ChatHistory::class.java), ChatHistoryRepositoryCustom {

    override fun findAll(
        chatRoomUuid: UUID,
        status: List<ChatHistory.Status>?,
        pageable: Pageable
    ): Page<ChatHistory> {
        val results: QueryResults<ChatHistory> = from(chatHistory)
            .join(chatHistory.chatRoom, chatRoom)
            .where(
                chatRoom.uuid.eq(chatRoomUuid)
                    .and(chatHistory.status.inFilterEmpty(status))
            )
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .orderBy(chatHistory.createdAt.desc())
            .fetchResults()

        return PageImpl(results.results, pageable, results.total)
    }
}
