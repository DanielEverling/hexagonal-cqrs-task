package com.cross.infra

import com.cross.logs.logger
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.spring.SpringTransactionManager
import org.slf4j.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
class DataSourceFactory {

    private val log = logger()

    @Bean
    fun transactionManager(dataSource: HikariDataSource): SpringTransactionManager =
            SpringTransactionManager(dataSource)
                    .also {
                        log.info ("USE SQL datasource: user=${dataSource.username} url=${dataSource.jdbcUrl}")
                    }

    @Bean
    fun persistenceExceptionTranslationPostProcessor() = PersistenceExceptionTranslationPostProcessor()

}