package com.astrog.telegrambot.internal.openai.persist

import com.astrog.telegrambot.persistStore
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile


@Profile("!$persistStore")
@Configuration
@EnableAutoConfiguration(
    exclude = [
        DataSourceAutoConfiguration::class,
        DataSourceTransactionManagerAutoConfiguration::class,
        HibernateJpaAutoConfiguration::class
    ]
)
class DisablePersistConfigurationOnProfile