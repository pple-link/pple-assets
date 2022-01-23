package link.pple.assets.configuration

import org.springframework.batch.core.configuration.annotation.BatchConfigurer
import org.springframework.batch.core.explore.JobExplorer
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.support.SimpleJobLauncher
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean
import org.springframework.transaction.PlatformTransactionManager
import javax.sql.DataSource

/**
 * @Author Heli
 */
class PpleBatchConfigurer(
    private val dataSource: DataSource,
    private val transactionManager: PlatformTransactionManager
) : BatchConfigurer {
    private val ppleJobRepository by lazy { createJobRepository() }
    private val ppleJobLauncher by lazy { createJobLauncher() }
    private val ppleJobExplorer by lazy { createJobExplorer() }

    override fun getTransactionManager(): PlatformTransactionManager = transactionManager

    override fun getJobRepository(): JobRepository = ppleJobRepository

    override fun getJobLauncher(): JobLauncher = ppleJobLauncher

    override fun getJobExplorer(): JobExplorer = ppleJobExplorer

    private fun createJobRepository(): JobRepository {
        val jobRepositoryFactoryBean = JobRepositoryFactoryBean()
        jobRepositoryFactoryBean.setDataSource(dataSource)
        jobRepositoryFactoryBean.transactionManager = transactionManager
        return jobRepositoryFactoryBean.`object`
    }

    private fun createJobLauncher(): JobLauncher {
        val jobLauncher = SimpleJobLauncher()
        jobLauncher.setJobRepository(ppleJobRepository)
        jobLauncher.afterPropertiesSet()
        return jobLauncher
    }

    private fun createJobExplorer(): JobExplorer {
        val factory = JobExplorerFactoryBean()
        factory.setDataSource(dataSource)
        factory.afterPropertiesSet()
        return factory.`object`
    }
}
