package link.pple.assets.domain.account.service

import link.pple.assets.domain.account.entity.Account
import link.pple.assets.domain.account.repository.AccountRepository
import link.pple.assets.util.notNull
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

/**
 * @Author Heli
 */
@Service
class AccountQuery(
    private val accountRepository: AccountRepository
) {

    fun getByEmailOrNull(email: String): Account? {
        return accountRepository.findByEmail(email)
    }

    fun getById(accountId: Long): Account {
        return accountRepository.findByIdOrNull(accountId).notNull { "Account 를 찾을 수 없음 [$accountId]" }
    }

    fun getByUuid(uuid: String): Account {
        return accountRepository.findByUuid(UUID.fromString(uuid)).notNull { "Account 를 찾을 수 없음 [$uuid]" }
    }
}
