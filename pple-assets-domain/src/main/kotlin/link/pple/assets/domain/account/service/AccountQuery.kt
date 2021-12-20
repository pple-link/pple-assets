package link.pple.assets.domain.account.service

import link.pple.assets.domain.account.entity.Account
import link.pple.assets.domain.account.repository.AccountRepository
import org.springframework.stereotype.Service

@Service
class AccountQuery(
    private val accountRepository: AccountRepository
) {

    fun getByEmailOrNull(email: String): Account? {
        return accountRepository.findByEmail(email)
    }

    fun getById(id: Long): Account {
        return accountRepository.findById(id).get()
    }
}
