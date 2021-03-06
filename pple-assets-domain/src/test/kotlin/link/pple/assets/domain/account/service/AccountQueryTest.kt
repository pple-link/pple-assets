package link.pple.assets.domain.account.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import link.pple.assets.domain.account.entity.Account
import link.pple.assets.domain.account.repository.AccountRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull
import java.util.*

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
        val insertEmail = "sun@example.com"
        every { accountRepository.findByEmailAndStatusIn(insertEmail) } returns Account.create(
            providerType = Account.ProviderType.KAKAO,
            providerAccountId = "1234",
            email = insertEmail,
            displayName = "Sun",
            profileImageUrl = "cdn.pple.link/a-b-c/d-e-f"
        )

        // when
        val actual = sut.getByEmailOrNull(insertEmail)

        // then
        expectThat(actual).isNotNull()
            .and {
                get { key } isEqualTo Account.ProviderKey(Account.ProviderType.KAKAO, "1234")
                get { email } isEqualTo insertEmail
                get { displayName } isEqualTo "Sun"
                get { profileImageUrl } isEqualTo "cdn.pple.link/a-b-c/d-e-f"
                get { gender } isEqualTo null
                get { birthDay } isEqualTo null
                get { phoneNumber } isEqualTo null
                get { blood } isEqualTo null
            }
    }

    @Test
    fun `조회 하려는 email 이 없으면 null 을 반환한다`() {
        // given
        val email = "sun@example.com"
        every { accountRepository.findByEmailAndStatusIn(email) } returns null

        // when
        val actual = sut.getByEmailOrNull(email)

        // then
        expectThat(actual).isNull()
    }

    @Test
    fun `id 로 Account 를 조회할 수 있다`() {
        // given
        val id = 42L
        every { accountRepository.findByIdOrNull(id) } returns Account.create(
            providerType = Account.ProviderType.KAKAO,
            providerAccountId = "1234",
            email = "sun@example.com",
            displayName = "Sun",
            profileImageUrl = "cdn.pple.link/a-b-c/d-e-f"
        )

        // when
        val actual = sut.getById(accountId = id)

        // then
        expectThat(actual)
            .and {
                get { key } isEqualTo Account.ProviderKey(Account.ProviderType.KAKAO, "1234")
                get { email } isEqualTo "sun@example.com"
                get { displayName } isEqualTo "Sun"
                get { profileImageUrl } isEqualTo "cdn.pple.link/a-b-c/d-e-f"
                get { gender } isEqualTo null
                get { birthDay } isEqualTo null
                get { phoneNumber } isEqualTo null
                get { blood } isEqualTo null
            }
    }

    @Test
    fun `id 로 Account 를 조회할 수 없으면 에러가 발생한다`() {
        // given
        val id = 42L
        every { accountRepository.findByIdOrNull(id) } returns null

        // when

        // then
        expectThrows<IllegalArgumentException> {
            sut.getById(accountId = id)
        }
    }

    @Test
    fun `uuid 로 Account 를 조회할 수 있다`() {
        // given
        val uuid = UUID.randomUUID()
        every { accountRepository.findByUuid(uuid) } returns Account.create(
            providerType = Account.ProviderType.KAKAO,
            providerAccountId = "1234",
            email = "sun@example.com",
            displayName = "Sun",
            profileImageUrl = "cdn.pple.link/a-b-c/d-e-f"
        )

        // when
        val actual = sut.getByUuid(uuid = uuid.toString())

        // then
        expectThat(actual)
            .and {
                get { key } isEqualTo Account.ProviderKey(Account.ProviderType.KAKAO, "1234")
                get { email } isEqualTo "sun@example.com"
                get { displayName } isEqualTo "Sun"
                get { profileImageUrl } isEqualTo "cdn.pple.link/a-b-c/d-e-f"
                get { gender } isEqualTo null
                get { birthDay } isEqualTo null
                get { phoneNumber } isEqualTo null
                get { blood } isEqualTo null
            }
    }

    @Test
    fun `uuid 로 Account 를 조회할 수 없으면 에러가 발생한다`() {
        // given
        val uuid = UUID.randomUUID()
        every { accountRepository.findByUuid(uuid) } returns null

        // when

        // then
        expectThrows<IllegalArgumentException> {
            sut.getByUuid(uuid = uuid.toString())
        }
    }
}
