package link.pple.assets.domain.account.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import link.pple.assets.domain.Blood
import link.pple.assets.domain.account.entity.Account
import link.pple.assets.domain.account.repository.AccountRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import java.time.LocalDate

/**
 * @Author Heli
 */
@ExtendWith(MockKExtension::class)
internal class AccountQueryTest {

    @MockK
    private lateinit var accountRepository: AccountRepository

    @InjectMockKs
    private lateinit var sut: AccountQuery

    @Test
    fun `email 로 Account 를 조회할 수 있다`() {
        // given
        val email = "sun@example.com"
        val birthDay = LocalDate.now()
        every { accountRepository.findByEmail(email) } returns Account.create(
            providerType = Account.ProviderType.KAKAO,
            providerAccountId = "1234",
            email = email,
            displayName = "Sun",
            birthDay = birthDay,
            gender = Account.Gender.MALE,
            phoneNumber = "+821012345678",
            profileImageUrl = "cdn.pple.link/a-b-c/d-e-f",
            blood = Blood(
                abo = Blood.ABO.B,
                rh = Blood.RH.POSITIVE
            )
        )

        // when
        val actual = sut.getByEmailOrNull(email)

        // then
        expectThat(actual).isNotNull()
            .and {
                get { key } isEqualTo Account.ProviderKey(Account.ProviderType.KAKAO, "1234")
                get { email } isEqualTo email
                get { displayName } isEqualTo "Sun"
                get { birthDay } isEqualTo birthDay
                get { gender } isEqualTo Account.Gender.MALE
                get { phoneNumber } isEqualTo "+821012345678"
                get { blood } isEqualTo Blood(Blood.ABO.B, Blood.RH.POSITIVE)
            }
    }

    @Test
    fun `조회 하려는 email 이 없으면 null 을 반환한다`() {
        // given
        val email = "sun@example.com"
        every { accountRepository.findByEmail(email) } returns null

        // when
        val actual = sut.getByEmailOrNull(email)

        // then
        expectThat(actual).isNull()
    }

    @Test
    fun `id 로 Account 를 조회할 수 있다`() {
        // given
        val id = 42L
        val birthDay = LocalDate.now()
        every { accountRepository.findByIdOrNull(id) } returns Account.create(
            providerType = Account.ProviderType.KAKAO,
            providerAccountId = "1234",
            email = "sun@example.com",
            displayName = "Sun",
            birthDay = birthDay,
            gender = Account.Gender.MALE,
            phoneNumber = "+821000000000",
            profileImageUrl = "cdn.pple.link/a-b-c/d-e-f",
            blood = Blood(
                abo = Blood.ABO.B,
                rh = Blood.RH.POSITIVE
            )
        )

        // when
        val actual = sut.getById(accountId = id)

        // then
        expectThat(actual)
            .and {
                get { key } isEqualTo Account.ProviderKey(Account.ProviderType.KAKAO, "1234")
                get { email } isEqualTo "sun@example.com"
                get { displayName } isEqualTo "Sun"
                get { birthDay } isEqualTo birthDay
                get { gender } isEqualTo Account.Gender.MALE
                get { phoneNumber } isEqualTo "+821000000000"
                get { blood } isEqualTo Blood(Blood.ABO.B, Blood.RH.POSITIVE)
            }
    }
}
