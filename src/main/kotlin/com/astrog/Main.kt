package com.astrog

import com.astrog.domain.GameProperty
import com.astrog.internal.property.TelegramBotProperty
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(
    TelegramBotProperty::class,
    GameProperty::class
)
@SpringBootApplication
class TelegramBotApplication

fun main(args: Array<String>) {
    runApplication<TelegramBotApplication>(*args)
}