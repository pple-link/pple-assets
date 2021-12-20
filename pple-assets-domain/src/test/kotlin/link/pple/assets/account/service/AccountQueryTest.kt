package link.pple.assets.account.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import link.pple.assets.domain.account.entity.Account
import link.pple.assets.domain.account.repository.AccountRepository
import link.pple.assets.domain.account.service.AccountQuery
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expectThat
import strikt.assertions.isEqualTo
import strikt.assertions.isNotNull
import strikt.assertions.isNull

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
        every { accountRepository.findByEmail(email) } returns Account.create(
            providerType = Account.ProviderType.KAKAO,
            providerAccountId = "1234",
            email = email,
            displayName = "Sun"
        )

        // when
        val actual = sut.getByEmailOrNull(email)

        // then
        expectThat(actual).isNotNull()
            .and {
                get { displayName } isEqualTo "Sun"
                get { email } isEqualTo email
                get { key } isEqualTo Account.ProviderKey(Account.ProviderType.KAKAO, "1234")
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
        every { accountRepository.findById(id).get() } returns Account.create(
            providerType = Account.ProviderType.KAKAO,
            providerAccountId = "1234",
            email = "sun@example.com",
            displayName = "Sun"
        )

        // when
        val actual = sut.getById(id = id)

        // then
        expectThat(actual) {
            get { displayName } isEqualTo "Sun"
            get { email } isEqualTo "sun@example.com"
            get { key } isEqualTo Account.ProviderKey(Account.ProviderType.KAKAO, "1234")
        }
    }
}
