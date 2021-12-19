package link.pple.assets.infrastructure.datasource

import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.transaction.annotation.EnableTransactionManagement
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
class DataSourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("pple.datasource")
    fun dataSourceProperties() = DataSourceProperties()

    @Bean
    @Primary
    fun dataSource(): DataSource {
        return dataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource::class.java).build()
    }
}
