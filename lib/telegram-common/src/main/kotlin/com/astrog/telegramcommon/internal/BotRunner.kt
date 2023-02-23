package com.astrog.telegramcommon.internal

import com.astrog.telegramcommon.api.TelegramService
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.util.PriorityQueue

@Component
class BotRunner(
    private val telegramService: TelegramService,
    private val processUpdateService: ProcessUpdateService,
) : CommandLineRunner {

    override fun run(args: Array<String>) {
        var lastUpdateId = Long.MAX_VALUE
        while (true) {
            val updates = telegramService.getUpdates(lastUpdateId)
            updates.lastOrNull()?.let { lastUpdate -> lastUpdateId = lastUpdate.updateId + 1 }
            updates.forEach { update -> processUpdateService.processUpdate(update) }
        }
    }
}