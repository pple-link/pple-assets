package link.pple.assets.interfaces.api.account

import link.pple.assets.configuration.auditor.AuditingApi
import link.pple.assets.domain.account.service.AccountCommand
import link.pple.assets.domain.account.service.AccountQuery
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * @Author Heli
 */
@Validated
@RestController
class AccountRestController(
    private val accountQuery: AccountQuery,
    private val accountCommand: AccountCommand
) {

    /**
     * UUID 로 회원 정보 조회
     * @Author Heli
     */
    @GetMapping(
        value = ["/account/api/v1/account/{accountUuid}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @AuditingApi
    fun getAccount(
        @PathVariable accountUuid: String
    ): AccountDto {
        val account = accountQuery.getByUuid(accountUuid)

        return account.toDto()
    }

    /**
     * 회원 가입: OAuth2 최초 인증, 계정에 추가 정보 없음
     * @Author Heli
     */
    @PostMapping(
        value = ["/account/api/v1/account"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createAccount(
        @RequestBody @Valid definitionDto: AccountCreateDefinitionDto
    ): AccountDto {
        val definition = definitionDto.toDefinition()
        val account = accountCommand.create(definition)
        return account.toDto()
    }

    /**
     * 회원 가입: OAuth2 가입 후 추가 정보 입력
     * @Author Heli
     */
    @PatchMapping(
        value = ["/account/api/v1/account"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun applyAccount(
        @RequestBody @Valid dto: AccountApplyDefinitionDto
    ): AccountDto {
        val definition = dto.toDefinition()
        val account = accountCommand.apply(definition)
        return account.toDto()
    }


    /**
     * 회원 정보 수정(DisplayName, ProfileImageUrl)
     * @Author Heli
     */
    @PatchMapping(
        value = ["/account/api/v1/account/{accountUuid}"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @AuditingApi
    fun updateAccount(
        @PathVariable accountUuid: String,
        @RequestBody @Valid definitionDto: AccountPatchDto
    ): AccountDto {

        val account = accountCommand.update(
            uuid = accountUuid,
            displayName = definitionDto.displayName,
            profileImageUrl = definitionDto.profileImageUrl
        )

        return account.toDto()
    }
}
