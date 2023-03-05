package com.astrog.telegramcommon.internal

import com.astrog.telegramcommon.api.TelegramService
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
@EnableScheduling
class BotRunner(
    private val telegramService: TelegramService,
    private val processUpdateService: ProcessUpdateService,
) {

    private var lastUpdateId = Long.MAX_VALUE

    @Scheduled(fixedRateString = "\${telegram.long-polling-timeout}", initialDelay = 1000)
    fun execute() {
        val updates = telegramService.getUpdates(lastUpdateId)
        updates.lastOrNull()?.let { lastUpdate -> lastUpdateId = lastUpdate.updateId + 1 }
        updates.forEach { update -> processUpdateService.processUpdate(update) }
    }
}