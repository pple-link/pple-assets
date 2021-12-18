package link.pple.assets.domain.account.repository

import link.pple.assets.domain.account.entity.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository : JpaRepository<Account, Long> {

    fun findByEmail(email: String): Account?
}
