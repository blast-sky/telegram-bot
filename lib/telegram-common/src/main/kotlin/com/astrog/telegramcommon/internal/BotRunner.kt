package com.astrog.telegramcommon.internal

import com.astrog.telegramcommon.api.TelegramService
import mu.KotlinLogging
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClientException

private val logger = KotlinLogging.logger { }

@Component
@EnableScheduling
class BotRunner(
    private val telegramService: TelegramService,
    private val processUpdateService: ProcessUpdateService,
) {

    private var lastUpdateId = Long.MAX_VALUE

    @Scheduled(fixedDelay = 10)
    fun execute() = try {
        val updates = telegramService.getUpdates(lastUpdateId)
        updates.lastOrNull()?.let { lastUpdate -> lastUpdateId = lastUpdate.updateId + 1 }
        updates.forEach { update -> processUpdateService.processUpdate(update) }
    } catch (ex: RestClientException) {
        logger.error(ex.stackTraceToString())
    }
}