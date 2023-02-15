package com.astrog

import com.astrog.domain.ProcessUpdateService
import com.astrog.domain.TelegramService
import com.astrog.internal.TelegramClient
import com.astrog.internal.property.TelegramBotProperty
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@EnableConfigurationProperties(TelegramBotProperty::class)
@SpringBootApplication
class TelegramBotApplication(
    private val telegramService: TelegramService,
    private val processUpdateService: ProcessUpdateService,
) : CommandLineRunner {

    override fun run(args: Array<String>) {
        var lastUpdateId = 0L
        while (true) {
            val updates = telegramService.getUpdates(lastUpdateId)
            updates.lastOrNull()?.let { lastUpdate -> lastUpdateId = lastUpdate.updateId + 1 }
            updates.forEach { update -> processUpdateService.processUpdate(update) }
        }
    }
}

fun main(args: Array<String>) {
    runApplication<TelegramBotApplication>(*args)
}