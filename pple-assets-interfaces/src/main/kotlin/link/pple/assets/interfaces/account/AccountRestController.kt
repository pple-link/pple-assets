package link.pple.assets.interfaces.account

import link.pple.assets.domain.account.entity.Account
import link.pple.assets.domain.account.service.AccountCommand
import link.pple.assets.domain.account.service.AccountDefinition
import link.pple.assets.domain.account.service.AccountQuery
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountRestController(
    private val accountQuery: AccountQuery,
    private val accountCommand: AccountCommand
) {

    @GetMapping(
        value = ["/account/api/v1/account"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getAccounts(): Account {
        accountCommand.create(
            definition = AccountDefinition(
                key = Account.ProviderKey(
                    type = Account.ProviderType.KAKAO,
                    id = "asd"
                ),
                email = "college@kakao.com",
                displayName = "Sun"
            )
        )
        return accountQuery.getByEmailOrNull("college@kakao.com")!!
    }
}
