package com.astrog.telegrambot

import com.astrog.openaiapi.internal.OpenAiProperty
import com.astrog.telegrambot.internal.GameProperty
import com.astrog.telegramcommon.internal.property.TelegramBotProperty
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(
    TelegramBotProperty::class,
    OpenAiProperty::class,
    GameProperty::class
)
@SpringBootApplication
class TelegramBotApplication

fun main(args: Array<String>) {
    runApplication<TelegramBotApplication>(*args)
}