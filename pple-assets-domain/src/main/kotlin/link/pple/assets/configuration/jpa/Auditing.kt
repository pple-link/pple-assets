package link.pple.assets.configuration.jpa

import org.springframework.data.domain.AuditorAware
import java.util.*
import javax.persistence.Embeddable

/**
 * @Author Heli
 */
@Embeddable
data class Auditor(
    val accountId: Long
) {
    val isSystem: Boolean get() = this.accountId == SYSTEM_ID

    enum class Type {
        USER, SYSTEM
    }

    companion object {
        const val SYSTEM_ID = -1L
        private val SYSTEM = Auditor(SYSTEM_ID)
        fun ofSystem() = SYSTEM
    }
}

object AuditorHolder {

    private val HOLDER = ThreadLocal<Auditor>()

    fun get(): Auditor = requireNotNull(HOLDER.get()) { "Auditor 가 비어 있습니다" }
    fun getOrNull(): Auditor? = HOLDER.get()
    fun set(auditor: Auditor) = HOLDER.set(auditor)
    fun clear() = HOLDER.remove()
}

internal class ThreadLocalAuditorAware : AuditorAware<Auditor> {
    override fun getCurrentAuditor(): Optional<Auditor> {
        return Optional.ofNullable(AuditorHolder.getOrNull())
    }
}
