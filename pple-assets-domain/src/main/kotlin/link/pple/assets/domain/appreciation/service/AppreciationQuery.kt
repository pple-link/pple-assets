package link.pple.assets.domain.appreciation.service

import link.pple.assets.domain.appreciation.repository.AppreciationRepository
import org.springframework.stereotype.Service

/**
 * @Author Heli
 */
@Service
class AppreciationQuery(
    private val appreciationRepository: AppreciationRepository
) {
}
