package com.astrog.telegramcommon.internal.runner

import com.astrog.telegramcommon.api.TelegramService
import com.astrog.telegramcommon.internal.runner.idholder.LastProcessedIdHolder
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
    private val updateDispatcher: UpdateDispatcher,
    private val lastProcessedIdHolder: LastProcessedIdHolder,
) {

    @Scheduled(fixedDelay = 10)
    fun execute() = try {
        val updates = telegramService.getUpdates(lastProcessedIdHolder.lastProcessedId)
        updates.lastOrNull()?.let { lastUpdate ->
            lastProcessedIdHolder.updateLastProcessedId(lastUpdate.updateId + 1)
        }
        updates.forEach { update -> updateDispatcher.dispatch(update) }
    } catch (ex: RestClientException) {
        logger.error(ex.stackTraceToString())
    }
}