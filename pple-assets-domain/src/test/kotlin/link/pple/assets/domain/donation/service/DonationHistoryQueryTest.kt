package link.pple.assets.domain.donation.service

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import link.pple.assets.domain.donation.repository.DonationHistoryRepository
import org.junit.jupiter.api.extension.ExtendWith

/**
 * @Author Heli
 */
@ExtendWith(MockKExtension::class)
internal class DonationHistoryQueryTest {

    @MockK
    private lateinit var donationHistoryRepository: DonationHistoryRepository

    @InjectMockKs
    private lateinit var donationHistoryQuery: DonationHistoryQuery

}
