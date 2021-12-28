package link.pple.assets.domain.appreciation.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import link.pple.assets.domain.appreciation.entity.Appreciation
import link.pple.assets.domain.appreciation.repository.AppreciationRepository
import link.pple.assets.domain.donation.entity.Donation
import link.pple.assets.domain.donation.service.DonationQuery
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.get
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEqualTo

/**
 * @Author Heli
 */
@ExtendWith(MockKExtension::class)
internal class AppreciationQueryTest {

    @MockK
    private lateinit var appreciationRepository: AppreciationRepository

    @MockK
    private lateinit var donationQuery: DonationQuery

    @InjectMockKs
    private lateinit var appreciationQuery: AppreciationQuery


    @Test
    fun `id 로 Appreciation 을 조회할 수 있다`() {
        // given
        val appreciation = Appreciation.create(
            content = "content",
            donation = mockk()
        )
        every { appreciationRepository.findByIdOrNull(EXIST_APPRECIATION_ID) } returns appreciation

        // when
        val actual = appreciationQuery.getById(EXIST_APPRECIATION_ID)

        // then
        expectThat(actual) {
            get { content } isEqualTo "content"
            get { donation } isNotEqualTo null
        }
    }

    @Test
    fun `id 로 Appreciation 을 조회시 존재하지 않으면 에러가 발생한다`() {
        // given
        every { appreciationRepository.findByIdOrNull(EXIST_APPRECIATION_ID) } returns null

        // when

        // then
        expectThrows<IllegalArgumentException> {
            appreciationQuery.getById(EXIST_APPRECIATION_ID)
        }
    }

    @Test
    fun `Donation 에 등록된 모든 감사 메시지를 조회한다`() {
        // given
        val donation = Donation.create(
            title = "title",
            content = "content",
            patient = mockk(),
            needCount = 5L
        )
        val firstAppreciation = Appreciation.create(
            content = "content1",
            donation = donation
        )
        val secondAppreciation = Appreciation.create(
            content = "content2",
            donation = donation
        )
        every { donationQuery.getById(EXIST_DONATION_ID) } returns donation
        every { appreciationRepository.findAllByDonation(donation) } returns listOf(
            firstAppreciation, secondAppreciation
        )

        // when
        val actual = appreciationQuery.getByDonationId(EXIST_DONATION_ID)

        // then
        expectThat(actual) {
            hasSize(2).and {
                get(0).and {
                    get { content } isEqualTo "content1"
                }
                get(1).and {
                    get { content } isEqualTo "content2"
                }
            }
        }
    }

    @Test
    fun `Donation 에 등록된 감사 메시지가 없으면 빈 리스트를 반환한다다`() {
        // given
        val donation = Donation.create(
            title = "title",
            content = "content",
            patient = mockk(),
            needCount = 5L
        )
        every { donationQuery.getById(EXIST_DONATION_ID) } returns donation
        every { appreciationRepository.findAllByDonation(donation) } returns emptyList()

        // when
        val actual = appreciationQuery.getByDonationId(EXIST_DONATION_ID)

        // then
        expectThat(actual) hasSize 0
    }

    companion object {
        private const val EXIST_APPRECIATION_ID = 42L
        private const val EXIST_DONATION_ID = 42L
    }
}
