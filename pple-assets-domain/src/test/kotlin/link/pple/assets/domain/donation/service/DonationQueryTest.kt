package link.pple.assets.domain.donation.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import link.pple.assets.domain.Blood
import link.pple.assets.domain.donation.entity.Donation
import link.pple.assets.domain.donation.repository.DonationRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.get
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
        every { donationRepository.load(uuid) } returns Donation.create(
            title = "Title",
            content = "Content",
            bloodProduct = listOf(
                Blood.Product.WHOLE,
                Blood.Product.PLATELET,
                Blood.Product.LEUKOCYTE,
                Blood.Product.PACKED_RED_BLOOD_CELL,
                Blood.Product.LEUKOCYTE_REDUCED_RED_BLOOD_CELL
            ),
            patient = mockk(),
            phoneNumber = "01096081327"
        )

        // when
        val actual = sut.getByUuid(uuid.toString())

        // then
        expectThat(actual) {
            get { title } isEqualTo "Title"
            get { content } isEqualTo "Content"
            get { bloodProduct } isEqualTo listOf(
                Blood.Product.WHOLE,
                Blood.Product.PLATELET,
                Blood.Product.LEUKOCYTE,
                Blood.Product.PACKED_RED_BLOOD_CELL,
                Blood.Product.LEUKOCYTE_REDUCED_RED_BLOOD_CELL
            )
            get { patient } isNotEqualTo null
            get { phoneNumber } isEqualTo "01096081327"
        }
    }


    @Test
    fun `존재 하지 않는 id 로 Donation 을 조회 시 에러가 발생한다`() {
        // given
        val uuid = UUID.randomUUID()
        every { donationRepository.load(uuid) } returns null

        // when

        // then
        expectThrows<IllegalArgumentException> {
            sut.getByUuid(uuid.toString())
        }
    }

    @Test
    fun `Donation 목록을 Page 로 조회할 수 있다`() {
        // given
        val firstDonation = Donation.create(
            title = "title",
            content = "content",
            bloodProduct = listOf(
                Blood.Product.WHOLE,
                Blood.Product.PLATELET,
                Blood.Product.LEUKOCYTE,
                Blood.Product.PACKED_RED_BLOOD_CELL,
                Blood.Product.LEUKOCYTE_REDUCED_RED_BLOOD_CELL
            ),
            patient = mockk(),
            phoneNumber = "01096081327"
        )
        val donations = listOf(firstDonation)
        val donationPages = PageImpl(donations, Pageable.ofSize(10), donations.size.toLong())
        every {
            donationRepository.load(
                emptyList(),
                createdAuditor = null,
                pageable = Pageable.ofSize(10)
            )
        } returns donationPages

        // when
        val actual = sut.getExecutionPage(
            status = emptyList(),
            createdAuditor = null,
            pageable = Pageable.ofSize(10)
        )

        // then
        expectThat(actual) {
            get { content.size } isEqualTo 1
            get { content }.and {
                get(0).and {
                    get { title } isEqualTo "title"
                    get { content } isEqualTo "content"
                    get { bloodProduct } isEqualTo listOf(
                        Blood.Product.WHOLE,
                        Blood.Product.PLATELET,
                        Blood.Product.LEUKOCYTE,
                        Blood.Product.PACKED_RED_BLOOD_CELL,
                        Blood.Product.LEUKOCYTE_REDUCED_RED_BLOOD_CELL
                    )
                    get { patient } isNotEqualTo null
                    get { phoneNumber } isEqualTo "01096081327"
                }
            }
        }
    }
}
