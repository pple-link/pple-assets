package link.pple.assets.domain.account.repository

import link.pple.assets.domain.account.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

/**
 * @Author Heli
 */
interface AccountRepository : JpaRepository<Account, Long> {

    fun findByEmailAndStatusIn(
        email: String,
        status: List<Account.Status> = listOf(Account.Status.ACTIVE, Account.Status.TEMP)
    ): Account?

    fun findByUuid(uuid: UUID): Account?
}
