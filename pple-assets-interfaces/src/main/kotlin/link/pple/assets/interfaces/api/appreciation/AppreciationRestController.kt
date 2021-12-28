package link.pple.assets.interfaces.api.appreciation

import link.pple.assets.configuration.auditor.AuditingApi
import link.pple.assets.domain.appreciation.service.AppreciationCommand
import link.pple.assets.domain.appreciation.service.AppreciationQuery
import org.springframework.http.MediaType
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * @Author Heli
 */
@Validated
@RestController
class AppreciationRestController(
    private val appreciationQuery: AppreciationQuery,
    private val appreciationCommand: AppreciationCommand
) {

    /**
     * Appreciation UUID 로 감사 메시지 조회
     * @Author Heli
     */
    @GetMapping(
        value = ["/appreciation/api/v1/appreciation/{appreciationUuid}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @AuditingApi
    fun getAppreciation(
        @PathVariable appreciationUuid: String
    ): AppreciationDto {
        val appreciation = appreciationQuery.getByUuid(uuid = appreciationUuid)

        return appreciation.toDto()
    }

    /**
     * Appreciation 목록 조회
     * @Param donorAccountId: 특정 사용자가 받은 감사 목록을 조회시 사용
     * @PAram donationId: 특정 사연에 등록된 감사 목록을 조회시 사용
     * @Author Heli
     */
    @GetMapping(
        value = ["/appreciation/api/v1/appreciation"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @AuditingApi
    fun getAllAppreciations(
        @RequestParam donorAccountId: Long?,
        @RequestParam donationId: Long?
    ): List<AppreciationDto> {

        val appreciations = appreciationQuery.getAll(
            donorAccountId = donorAccountId,
            donationId = donationId
        )

        return appreciations.map {
            it.toDto()
        }
    }

    /**
     * Appreciation 등록
     * @Author Heli
     */
    @PostMapping(
        value = ["/appreciation/api/v1/appreciation"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @AuditingApi
    fun createAppreciation(
        @RequestBody @Valid definitionDto: AppreciationDefinitionDto
    ): AppreciationDto {

        val definition = definitionDto.toDefinition()
        val appreciation = appreciationCommand.create(definition = definition)

        return appreciation.toDto()
    }
}
