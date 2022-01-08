package link.pple.assets.domain.account.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import link.pple.assets.domain.account.entity.Account
import link.pple.assets.domain.account.repository.AccountRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import java.time.LocalDate
import java.util.*

/**
 * @Author Heli
 */
@ExtendWith(MockKExtension::class)
internal class AccountCommandTest {

    @MockK
    private lateinit var accountRepository: AccountRepository

    @MockK
    private lateinit var accountQuery: AccountQuery

    @InjectMockKs
    private lateinit var sut: AccountCommand

    @Test
    fun `계정을 생성할 수 있다`() {
        // given
        val insertBirthDay = LocalDate.now()
        val definition = AccountCreateDefinition(
            key = Account.ProviderKey(
                type = Account.ProviderType.KAKAO,
                id = "1234"
            ),
            email = "college@kakao.com",
            displayName = "Sun",
            profileImageUrl = "cdn.pple.link/a-b-c/d-e-f",
        )
        every { accountQuery.getByEmailOrNull("college@kakao.com") } returns null
        every { accountRepository.save(any()) } returns Account.from(definition)

        // when
        val actual = sut.create(definition = definition)

        // then
        expectThat(actual) {
            get { key } isEqualTo Account.ProviderKey(Account.ProviderType.KAKAO, "1234")
            get { email } isEqualTo "college@kakao.com"
            get { displayName } isEqualTo "Sun"
            get { profileImageUrl } isEqualTo "cdn.pple.link/a-b-c/d-e-f"
            get { phoneNumber } isEqualTo null
            get { birthDay } isEqualTo null
            get { gender } isEqualTo null
            get { blood } isEqualTo null
        }
    }

    @Test
    fun `이미 존재하는 이메일로는 계정을 생성할 수 없다`() {
        // given
        val definition = AccountCreateDefinition(
            key = Account.ProviderKey(
                type = Account.ProviderType.KAKAO,
                id = "1234"
            ),
            email = "college@kakao.com",
            displayName = "Sun",
            profileImageUrl = "cdn.pple.link/a-b-c/d-e-f"
        )
        val account = Account.from(definition)
        every { accountQuery.getByEmailOrNull("college@kakao.com") } returns account
        every { accountRepository.save(account) } returns account

        // then
        expectThrows<IllegalArgumentException> {
            sut.create(definition = definition)
        }
    }

    @Test
    fun `id 로 계정 정보를 수정할 수 있다`() {
        // given
        val definition = AccountCreateDefinition(
            key = Account.ProviderKey(
                type = Account.ProviderType.KAKAO,
                id = "1234"
            ),
            email = "college@kakao.com",
            displayName = "Sun",
            profileImageUrl = "cdn.pple.link/a-b-c/d-e-f"
        )
        val account = Account.from(definition)
        every { accountQuery.getById(EXIST_ACCOUNT_ID) } returns account
        every { accountRepository.save(any()) } returns account

        // when
        val actual = sut.update(
            accountId = EXIST_ACCOUNT_ID,
            displayName = "modified",
            profileImageUrl = "modified"
        )

        // then
        verify {
            accountRepository.save(account)
        }
        expectThat(actual) {
            get { key } isEqualTo Account.ProviderKey(Account.ProviderType.KAKAO, "1234")
            get { email } isEqualTo "college@kakao.com"
            get { displayName } isEqualTo "modified"
            get { profileImageUrl } isEqualTo "modified"
            get { gender } isEqualTo null
            get { birthDay } isEqualTo null
            get { phoneNumber } isEqualTo null
            get { blood } isEqualTo null
        }
    }

    @Test
    fun `uuid 로 계정 정보를 수정할 수 있다`() {
        // given
        val definition = AccountCreateDefinition(
            key = Account.ProviderKey(
                type = Account.ProviderType.KAKAO,
                id = "1234"
            ),
            email = "college@kakao.com",
            displayName = "Sun",
            profileImageUrl = "cdn.pple.link/a-b-c/d-e-f"
        )
        val account = Account.from(definition)
        every { accountQuery.getByUuid(EXIST_ACCOUNT_UUID.toString()) } returns account
        every { accountRepository.save(any()) } returns account

        // when
        val actual = sut.update(
            uuid = EXIST_ACCOUNT_UUID.toString(),
            displayName = "modified",
            profileImageUrl = "modified"
        )

        // then
        verify {
            accountRepository.save(account)
        }
        expectThat(actual) {
            get { key } isEqualTo Account.ProviderKey(Account.ProviderType.KAKAO, "1234")
            get { email } isEqualTo "college@kakao.com"
            get { displayName } isEqualTo "modified"
            get { profileImageUrl } isEqualTo "modified"
            get { gender } isEqualTo null
            get { birthDay } isEqualTo null
            get { phoneNumber } isEqualTo null
            get { blood } isEqualTo null
        }
    }

    companion object {
        private const val EXIST_ACCOUNT_ID = 42L
        private val EXIST_ACCOUNT_UUID = UUID.randomUUID()
    }
}
