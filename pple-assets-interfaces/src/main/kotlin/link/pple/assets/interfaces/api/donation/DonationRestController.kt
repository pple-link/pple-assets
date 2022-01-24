package link.pple.assets.interfaces.api.donation

import link.pple.assets.configuration.auditor.AuditingApi
import link.pple.assets.configuration.jpa.AuditorHolder
import link.pple.assets.domain.donation.entity.Donation
import link.pple.assets.domain.donation.service.DonationCommand
import link.pple.assets.domain.donation.service.DonationQuery
import link.pple.assets.interfaces.api.PagedDto
import link.pple.assets.interfaces.api.pagedDto
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * @Author Heli
 */
@RestController
class DonationRestController(
    private val donationQuery: DonationQuery,
    private val donationCommand: DonationCommand
) {

    @PostMapping(
        value = ["/donation/api/v1/donation"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @AuditingApi
    fun createDonation(
        @RequestBody @Valid dto: DonationDefinitionDto
    ): DonationDto {
        val definition = dto.toDefinition()

        val donation = donationCommand.create(
            definition = definition
        )

        return donation.toDto()
    }

    @GetMapping(
        value = ["/donation/api/v1/donation/{donationUuid}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @AuditingApi
    fun getDonation(
        @PathVariable donationUuid: String
    ): DonationDto {

        val donation = donationQuery.getByUuid(
            donationUuid = donationUuid
        )

        return donation.toDto()
    }

    @GetMapping(
        value = ["/donation/api/v1/donation"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getDonations(
        @RequestParam(required = false) status: List<String>?,
        @PageableDefault pageable: Pageable
    ): PagedDto<DonationDto> {

        val executionStatus = status?.map { Donation.Status.from(it) }

        val donations = donationQuery.getExecutionPage(
            status = executionStatus,
            createdAuditor = null,
            pageable = pageable
        )
        return donations.pagedDto { it.toDto() }
    }

    @GetMapping(
        value = ["/donation/api/v1/donation/own"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @AuditingApi
    fun getDonationsByOwn(
        @RequestParam(required = false) status: List<String>?,
        @PageableDefault pageable: Pageable
    ): PagedDto<DonationDto> {

        val executionStatus = status?.map { Donation.Status.from(it) }

        val auditor = AuditorHolder.get()

        val donations = donationQuery.getExecutionPage(
            status = executionStatus,
            createdAuditor = auditor,
            pageable = pageable
        )
        return donations.pagedDto { it.toDto() }
    }

    @PatchMapping(
        value = ["/donation/api/v1/donation/{donationUuid}/renew"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @AuditingApi
    fun renewDonation(
        @PathVariable donationUuid: String
    ): DonationDto {

        val donation = donationCommand.renew(donationUuid)

        return donation.toDto()
    }
}
