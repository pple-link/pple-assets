package link.pple.assets.interfaces.api.donation

import link.pple.assets.configuration.auditor.AuditingApi
import link.pple.assets.configuration.jpa.AuditorHolder
import link.pple.assets.domain.account.service.AccountQuery
import link.pple.assets.domain.donation.entity.DonationHistory
import link.pple.assets.domain.donation.service.DonationHistoryCommand
import link.pple.assets.domain.donation.service.DonationHistoryQuery
import link.pple.assets.domain.donation.service.DonationQuery
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

/**
 * @Author Heli
 */
@RestController
class DonationHistoryRestController(
    private val donationHistoryQuery: DonationHistoryQuery,
    private val donationHistoryCommand: DonationHistoryCommand,
    private val accountQuery: AccountQuery,
    private val donationQuery: DonationQuery
) {

    @GetMapping(
        value = ["/donation/api/v1/donation/history"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @AuditingApi
    fun getDonationHistories(
        @RequestParam(required = false) donorAccountUuid: String?,
        @RequestParam(required = false) donationUuid: String?,
        @RequestParam(required = false) steps: List<DonationHistory.Step>?
    ): List<DonationHistoryDto> {

        val histories = donationHistoryQuery.getAll(
            donorAccountUuid = donorAccountUuid,
            donationUuid = donationUuid,
            steps = steps
        )

        return histories.map {
            it.toDto()
        }
    }

    @PostMapping(
        value = ["/donation/api/v1/donation/{donationUuid}/history"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @AuditingApi
    fun createDonationHistory(
        @PathVariable donationUuid: String
    ): DonationHistoryDto {

        val auditor = AuditorHolder.get()

        val donation = donationQuery.getByUuid(donationUuid)
        val account = accountQuery.getById(auditor.accountId)

        val donationHistory = donationHistoryCommand.create(
            donation = donation,
            donorAccount = account
        )

        return donationHistory.toDto()
    }
}
