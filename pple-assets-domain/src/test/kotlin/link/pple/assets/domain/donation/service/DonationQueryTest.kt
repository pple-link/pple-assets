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
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEqualTo
import java.util.*

/**
 * @Author Heli
 */
@ExtendWith(MockKExtension::class)
internal class DonationQueryTest {

    @MockK
    private lateinit var donationRepository: DonationRepository

    @InjectMockKs
    private lateinit var sut: DonationQuery

    @Test
    fun `Uuid 로 Donation 을 조회할 수 있다`() {
        // given
        val uuid = UUID.randomUUID()
        every { donationRepository.findByUuid(uuid) } returns Donation.create(
            title = "Title",
            content = "Content",
            patient = mockk(),
            needCount = 5L
        )

        // when
        val actual = sut.getByUuid(uuid.toString())

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
        val uuid = UUID.randomUUID()
        every { donationRepository.findByUuid(uuid) } returns null

        // when

        // then
        expectThrows<IllegalArgumentException> {
            sut.getByUuid(uuid.toString())
        }
    }
}
