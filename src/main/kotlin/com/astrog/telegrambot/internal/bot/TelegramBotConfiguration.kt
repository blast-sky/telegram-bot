package com.astrog.telegrambot.internal.bot

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.dispatch
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TelegramBotConfiguration {

    @Bean
    fun telegramBot(telegramBotProperty: TelegramBotProperty, dispatchers: Set<Dispatcher>): Bot {
        val builder = Bot.Builder().apply {
            token = telegramBotProperty.botToken
            timeout = telegramBotProperty.longPollingTimeout
        }
        builder.dispatch { dispatchers.forEach { it.invoke(this) } }
        return builder.build()
    }
}