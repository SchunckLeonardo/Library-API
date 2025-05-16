package io.github.libraryapi.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

const val MAXIMUM_LIFETIME_CONNECTION: Long = 600_000
const val DATABASE_CONNECTION_TIMEOUT: Long = 100_000

@Configuration
class DatabaseConfiguration {

    @Value("\${spring.datasource.url}")
    private lateinit var url: String

    @Value("\${spring.datasource.username}")
    private lateinit var username: String

    @Value("\${spring.datasource.password}")
    private lateinit var password: String

    @Value("\${spring.datasource.driver-class-name}")
    private lateinit var driver: String

    // @Bean
    fun dataSource(): DataSource {
        val ds = DriverManagerDataSource()

        ds.url = url
        ds.username = username
        ds.password = password
        ds.setDriverClassName(driver)
        return ds
    }

    @Bean
    fun hikariDataSource(): DataSource {
        val config = HikariConfig()

        config.jdbcUrl = url
        config.username = username
        config.password = password
        config.driverClassName = driver

        config.maximumPoolSize = 10
        config.minimumIdle = 1
        config.poolName = "library-db-pool"
        config.maxLifetime = MAXIMUM_LIFETIME_CONNECTION
        config.connectionTimeout = DATABASE_CONNECTION_TIMEOUT
        config.connectionTestQuery = "SELECT 1"

        return HikariDataSource(
            config
        )
    }

}