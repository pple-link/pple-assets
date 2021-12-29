package link.pple.assets.interfaces.api.donation

import link.pple.assets.domain.donation.entity.DonationHistory
import link.pple.assets.domain.donation.service.DonationHistoryQuery
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 * @Author Heli
 */
@RestController
class DonationHistoryRestController(
    private val donationHistoryQuery: DonationHistoryQuery
) {

    @GetMapping(
        value = ["/donation/api/v1/donation/history"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getAllDonationHistories(
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
}
