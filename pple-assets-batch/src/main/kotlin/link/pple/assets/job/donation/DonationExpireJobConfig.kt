package link.pple.assets.job.donation

import link.pple.assets.domain.donation.entity.Donation
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.JpaPagingItemReader
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.persistence.EntityManagerFactory

/**
 * @Author Heli
 */
@Configuration
class DonationExpireJobConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val resourcelessTransactionManager: PlatformTransactionManager,
    private val emf: EntityManagerFactory
) {

    @Bean
    fun donationExpireJob(): Job = jobBuilderFactory["donationExpireJob"]
        .start(donationExpireStep())
//        .preventRestart()
        .build()

    @Bean
    @JobScope
    fun donationExpireStep(): Step = stepBuilderFactory["donationExpireStep"]
        .transactionManager(resourcelessTransactionManager)
        .chunk<Donation, Donation>(1_000)
        .reader(donationExpireTargetReader())
        .processor(ItemProcessor {
            it.apply {
                this.status = Donation.Status.COMPLETE
            }
        })
        .writer(donationExpireWriter())
        .build()

    @Bean
    fun donationExpireTargetReader(): JpaPagingItemReader<Donation> {
        val now = LocalDateTime.now()
        return JpaPagingItemReaderBuilder<Donation>()
            .name("donationExpireTargetReader")
            .entityManagerFactory(emf)
            .pageSize(1_000)
            .queryString(
                """
                SELECT d
                FROM Donation d
                WHERE d.status = 'ACTIVE' and FLOOR(HOUR(timediff(${now.toTimeStamp()}, GREATEST(d.createdAt, COALESCE(d.lastRenewedAt, 0)))) / $ONE_DAY_HOURS) >= $RENEWED_INTERVAL_DAYS
            """.trimIndent()
            )
            .build()
    }

    private fun donationExpireWriter(): JpaItemWriter<Donation> =
        JpaItemWriterBuilder<Donation>()
            .entityManagerFactory(emf)
//            .usePersist(true)
            .build()

    companion object {

        private const val ONE_DAY_HOURS = 24
        private const val RENEWED_INTERVAL_DAYS = 7
    }
}

private fun LocalDateTime.toTimeStamp(): String {
    return "TIMESTAMP('${this.toIso()}')"
}

private fun LocalDateTime.toIso(): String = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(this)
