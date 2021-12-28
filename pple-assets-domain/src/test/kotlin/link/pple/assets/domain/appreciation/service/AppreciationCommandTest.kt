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
import strikt.assertions.isEqualTo
import strikt.assertions.isNotEqualTo

/**
 * @Author Heli
 */
@ExtendWith(MockKExtension::class)
internal class AppreciationCommandTest {

    @MockK
    private lateinit var appreciationRepository: AppreciationRepository

    @MockK
    private lateinit var donationQuery: DonationQuery

    @InjectMockKs
    private lateinit var sut: AppreciationCommand

    @Test
    fun `감사 메시지를 작성할 수 있다`() {
        // given
        val definition = AppreciationDefinition(
            content = "content",
            donationUuid = EXIST_DONATION_UUID
        )
        val donation = Donation.create(
            title = "title",
            content = "content",
            patient = mockk(),
            needCount = 5L
        )
        every { donationQuery.getByUuid(EXIST_DONATION_UUID) } returns donation
        every { appreciationRepository.save(any()) } returns Appreciation.create(
            content = definition.content,
            donation = donation
        )

        // when
        val actual = sut.create(definition)

        // then
        expectThat(actual) {
            get { content } isEqualTo "content"
            get { donation }.and {
                get { title } isEqualTo "title"
                get { content } isEqualTo "content"
                get { patient } isNotEqualTo null
                get { needCount } isEqualTo 5L
            }
        }
    }

    companion object {
        private const val EXIST_DONATION_UUID = "uuid"
    }
}
