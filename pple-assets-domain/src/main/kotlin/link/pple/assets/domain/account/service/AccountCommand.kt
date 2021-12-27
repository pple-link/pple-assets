package link.pple.assets.domain.account.service

import link.pple.assets.configuration.jpa.Jpa.PPLE_TRANSACTION_MANAGER
import link.pple.assets.domain.account.entity.Account
import link.pple.assets.domain.account.repository.AccountRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * @Author Heli
 */
@Transactional(PPLE_TRANSACTION_MANAGER)
@Service
class AccountCommand(
    private val accountRepository: AccountRepository,
    private val accountQuery: AccountQuery
) {

    fun create(definition: AccountDefinition): Account {
        val existAccount = accountQuery.getByEmailOrNull(definition.email)
        require(existAccount == null) { "이미 Account 가 존재합니다. $existAccount" }

        val account = Account.from(definition)

        return accountRepository.save(account)
    }

    fun update(accountId: Long, displayName: String, profileImageUrl: String): Account {
        val account = accountQuery.getById(accountId)

        return update(account = account, displayName = displayName, profileImageUrl = profileImageUrl)
    }

    fun update(uuid: String, displayName: String, profileImageUrl: String?): Account {
        val account = accountQuery.getByUuid(uuid)

        return update(account = account, displayName = displayName, profileImageUrl = profileImageUrl)
    }

    private fun update(account: Account, displayName: String, profileImageUrl: String?): Account {
        account.update(displayName = displayName, profileImageUrl = profileImageUrl)

        return accountRepository.save(account)
    }
}
