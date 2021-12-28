package link.pple.assets.domain.donation.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import link.pple.assets.domain.donation.entity.Donation
import link.pple.assets.domain.donation.repository.DonationRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEqualTo
import java.time.LocalDate

/**
 * @Author Heli
 */
@ExtendWith(MockKExtension::class)
internal class DonationQueryTest {

    @MockK
    private lateinit var donationRepository: DonationRepository

    @InjectMockKs
    private lateinit var donationQuery: DonationQuery

    @Test
    fun `id 로 Donation 을 조회할 수 있다`() {
        // given
        val now = LocalDate.now()
        every { donationRepository.findByIdOrNull(EXIST_DONATION_ID) } returns Donation.create(
            title = "Title",
            content = "Content",
            patient = mockk(),
            needCount = 5L
        )

        // when
        val actual = donationQuery.getById(EXIST_DONATION_ID)

        // then
        expectThat(actual) {
            get { title } isEqualTo "Title"
            get { content } isEqualTo "Content"
            get { patient } isNotEqualTo null
            get { needCount } isEqualTo 5L
        }
    }


    @Test
    fun `존재 하지 않는 id 로 Donation 을 조회 시 에러가 발생한다`() {
        // given
        every { donationRepository.findByIdOrNull(EXIST_DONATION_ID) } returns null

        // when

        // then
        expectThrows<IllegalArgumentException> {
            donationQuery.getById(EXIST_DONATION_ID)
        }
    }

    companion object {
        private const val EXIST_DONATION_ID = 42L
    }
}
