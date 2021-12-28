package link.pple.assets.interfaces.api.donation

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
        @RequestParam donorAccountUuid: String?,
        @RequestParam donationUuid: String?
    ): List<DonationHistoryDto> {

        val histories = donationHistoryQuery.getAll(
            donorAccountUuid = donorAccountUuid,
            donationUuid = donationUuid
        )

        return histories.map {
            it.toDto()
        }
    }
}
