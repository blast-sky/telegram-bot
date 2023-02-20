package com.astrog.telegrambot

import com.astrog.telegrambot.internal.GameProperty
import com.astrog.telegramcommon.internal.property.TelegramBotProperty
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@EnableConfigurationProperties(
    TelegramBotProperty::class,
    GameProperty::class
)
@SpringBootApplication
class TelegramBotApplication

fun main(args: Array<String>) {
    runApplication<TelegramBotApplication>(*args)
}