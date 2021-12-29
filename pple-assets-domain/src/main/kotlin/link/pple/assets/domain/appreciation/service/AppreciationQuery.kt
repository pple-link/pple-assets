package link.pple.assets.domain.appreciation.service

import link.pple.assets.domain.appreciation.entity.Appreciation
import link.pple.assets.domain.appreciation.repository.AppreciationRepository
import link.pple.assets.infrastructure.util.toUUID
import link.pple.assets.util.notNull
import org.springframework.stereotype.Service

/**
 * @Author Heli
 */
@Service
class AppreciationQuery(
    private val appreciationRepository: AppreciationRepository
) {

    fun getByUuid(uuid: String): Appreciation {
        return appreciationRepository.findByUuid(uuid.toUUID()).notNull { "Appreciation 를 찾을 수 없음 [$uuid]" }
    }

    fun getAll(
        donorAccountUuid: String?,
        donationUuid: String?,
        status: List<Appreciation.Status>?
    ): List<Appreciation> {
        return appreciationRepository.findAll(
            donorAccountUuid = donorAccountUuid?.toUUID(),
            donationUuid = donationUuid?.toUUID(),
            status = status
        )
    }
}
