package link.pple.assets.configuration.jpa

import link.pple.assets.domain.Domain
import org.hibernate.cfg.AvailableSettings
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

/**
 * @Author Heli
 */
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(
    entityManagerFactoryRef = Jpa.PPLE_ENTITY_MANAGER,
    transactionManagerRef = Jpa.PPLE_TRANSACTION_MANAGER,
    basePackageClasses = [Domain::class]
)
class JpaConfiguration(
    private val dataSource: DataSource
) {

    @Bean(Jpa.PPLE_ENTITY_MANAGER)
    fun entityManagerFactory(builder: EntityManagerFactoryBuilder): LocalContainerEntityManagerFactoryBean {

        val properties = mapOf(
            AvailableSettings.USE_SECOND_LEVEL_CACHE to false.toString(),
            AvailableSettings.USE_QUERY_CACHE to false.toString()
        )

        return builder
            .dataSource(dataSource)
            .packages(Domain::class.java)
            .properties(properties)
            .persistenceUnit(Jpa.PPLE_PERSISTENCE_UNIT)
            .build()
    }

    @Bean(Jpa.PPLE_TRANSACTION_MANAGER)
    @Primary
    fun transactionManager(factory: EntityManagerFactory): JpaTransactionManager = JpaTransactionManager(factory)

    @Bean(Jpa.AUDITOR_AWARE)
    fun auditorAware(): AuditorAware<Auditor> = ThreadLocalAuditorAware()
}
