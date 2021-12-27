package link.pple.assets.domain.appreciation.repository

import link.pple.assets.domain.appreciation.entity.Appreciation
import org.springframework.data.jpa.repository.JpaRepository

/**
 * @Author Heli
 */
interface AppreciationRepository : JpaRepository<Appreciation, Long> {

}
