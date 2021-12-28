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
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.get
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEqualTo
import java.util.*

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
    private lateinit var sut: AppreciationQuery


    @Test
    fun `uuid 로 Appreciation 을 조회할 수 있다`() {
        // given
        val appreciation = Appreciation.create(
            content = "content",
            donation = mockk()
        )
        every { appreciationRepository.findByUuid(EXIST_APPRECIATION_UUID) } returns appreciation

        // when
        val actual = sut.getByUuid(EXIST_APPRECIATION_UUID.toString())

        // then
        expectThat(actual) {
            get { content } isEqualTo "content"
            get { donation } isNotEqualTo null
        }
    }

    @Test
    fun `uuid 로 Appreciation 을 조회시 존재하지 않으면 에러가 발생한다`() {
        // given
        every { appreciationRepository.findByUuid(EXIST_APPRECIATION_UUID) } returns null

        // when

        // then
        expectThrows<IllegalArgumentException> {
            sut.getByUuid(EXIST_APPRECIATION_UUID.toString())
        }
    }

    @Test
    fun `모든 감사 메시지를 조회한다`() {
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
        val donorAccountUuid = UUID.randomUUID()
        val donationUuid = UUID.randomUUID()
        every { appreciationRepository.findAll(donorAccountUuid, donationUuid) } returns listOf(
            firstAppreciation, secondAppreciation
        )

        // when
        val actual = sut.getAll(
            donorAccountUuid = donorAccountUuid.toString(),
            donationUuid = donationUuid.toString()
        )

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
    fun `등록된 감사 메시지가 없으면 빈 리스트를 반환한다`() {
        // given
        val donorAccountUuid = UUID.randomUUID()
        val donationUuid = UUID.randomUUID()
        every { appreciationRepository.findAll(donorAccountUuid, donationUuid) } returns emptyList()

        // when
        val actual = sut.getAll(
            donorAccountUuid = donorAccountUuid.toString(),
            donationUuid = donationUuid.toString()
        )

        // then
        expectThat(actual) hasSize 0
    }

    companion object {
        private val EXIST_APPRECIATION_UUID = UUID.randomUUID()
    }
}
