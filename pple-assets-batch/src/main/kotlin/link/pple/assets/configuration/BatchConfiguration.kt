package link.pple.assets.configuration

import org.springframework.batch.core.configuration.annotation.BatchConfigurer
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.support.transaction.ResourcelessTransactionManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import javax.sql.DataSource

/**
 * @Author Heli
 */
@Configuration
@EnableBatchProcessing
class BatchConfiguration {

    @Bean
    @ConfigurationProperties("pple.batch.datasource")
    fun batchDataSourceProperties() = DataSourceProperties()

    @Bean(name = ["batchDataSource"])
    @ConfigurationProperties("pple.batch.datasource.hikari")
    fun batchDataSource(): DataSource {
        return batchDataSourceProperties().initializeDataSourceBuilder().build()
    }


    @Bean
    @Primary
    fun batchConfigurer(@Qualifier("batchDataSource") batchDataSource: DataSource): BatchConfigurer {
        return PpleBatchConfigurer(batchDataSource, DataSourceTransactionManager(batchDataSource))
    }

    @Bean
    fun resourcelessTransactionManager(): ResourcelessTransactionManager = ResourcelessTransactionManager()
}
