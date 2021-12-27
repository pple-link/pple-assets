package link.pple.assets.domain.term.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import link.pple.assets.domain.term.entity.Term
import link.pple.assets.domain.term.repository.TermRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.get
import strikt.assertions.hasSize
import strikt.assertions.isEqualTo

/**
 * @Author Heli
 */
@ExtendWith(MockKExtension::class)
internal class TermQueryTest {

    @MockK
    private lateinit var termRepository: TermRepository

    @InjectMockKs
    private lateinit var termQuery: TermQuery

    @Test
    fun `id 로 Term 을 찾을 수 있다`() {
        // given
        val term = Term.create(
            title = "Title",
            content = "Content",
            required = true,
            seq = 1L
        )
        every { termRepository.load(EXIST_TERM_ID) } returns term

        // when
        val actual = termQuery.getById(EXIST_TERM_ID)

        // then
        expectThat(actual) {
            get { title } isEqualTo "Title"
            get { content } isEqualTo "Content"
            get { required } isEqualTo true
            get { seq } isEqualTo 1L
            get { status } isEqualTo Term.Status.ACTIVE
        }
    }

    @Test
    fun `id 로 Term 을 찾을 때 존재하지 않으면 에러가 발생한다`() {
        // given
        every { termRepository.load(EXIST_TERM_ID) } returns null

        // when

        // then
        expectThrows<IllegalArgumentException> {
            termQuery.getById(EXIST_TERM_ID)
        }
    }

    @Test
    fun `모든 Term 을 조회할 수 있다`() {
        // given
        val termFirst = Term.create(
            title = "Title",
            content = "Content",
            required = true,
            seq = 1L
        )
        val termSecond = Term.create(
            title = "Title",
            content = "Content",
            required = true,
            seq = 2L
        )
        every { termRepository.load() } returns listOf(
            termFirst, termSecond
        )

        // when
        val actual = termQuery.getAll()

        // then
        expectThat(actual) {
            hasSize(2)
            get(0).and {
                get { title } isEqualTo "Title"
                get { content } isEqualTo "Content"
                get { required } isEqualTo true
                get { seq } isEqualTo 1L
                get { status } isEqualTo Term.Status.ACTIVE
            }
            get(1).and {
                get { title } isEqualTo "Title"
                get { content } isEqualTo "Content"
                get { required } isEqualTo true
                get { seq } isEqualTo 2L
                get { status } isEqualTo Term.Status.ACTIVE
            }
        }
    }

    companion object {
        private const val EXIST_TERM_ID = 42L
    }
}
