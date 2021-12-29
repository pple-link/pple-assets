package link.pple.assets.domain.chat.repository

import link.pple.assets.domain.chat.entity.ChatRoomConnection
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @Author Heli
 */
interface ChatRoomConnectionRepository : JpaRepository<ChatRoomConnection, Long> {
}
