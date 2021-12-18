package link.pple.assets.domain.account.service

import link.pple.assets.domain.account.entity.Account
import link.pple.assets.domain.account.repository.AccountRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class AccountCommand(
    private val accountRepository: AccountRepository
) {

    fun create(definition: AccountDefinition): Account {

        val account = Account.create(
            providerType = definition.key.type,
            providerAccountId = definition.key.id,
            email = definition.email,
            displayName = definition.displayName
        )

        return accountRepository.save(account)
    }
}
