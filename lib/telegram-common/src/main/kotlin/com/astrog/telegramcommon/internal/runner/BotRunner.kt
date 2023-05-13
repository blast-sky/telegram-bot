package com.astrog.telegramcommon.internal.runner

import com.astrog.telegramcommon.api.TelegramService
import com.astrog.telegramcommon.domain.model.update.RawUpdate
import com.astrog.telegramcommon.internal.client.UpdateMapper
import com.astrog.telegramcommon.internal.runner.idholder.IdHolder
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.runBlocking
import mu.KotlinLogging
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

private val logger = KotlinLogging.logger { }


@Component
@EnableScheduling
class BotRunner(
    private val telegramService: TelegramService,
    private val updateDispatcher: UpdateDispatcher,
    private val updateMapper: UpdateMapper,
    private val idHolder: IdHolder,
) {

    private val exceptionLogger = CoroutineExceptionHandler { _, throwable ->
        logger.error(throwable.stackTraceToString())
    }

    @Scheduled(fixedDelay = 10)
    fun execute() = runBlocking(exceptionLogger) {
        val lastProcessedId = idHolder.id
        val updates = telegramService.getUpdates(lastProcessedId + 1)
        updateIdIfUpdatesNotEmpty(updates)
        val mappedUpdates = updateMapper.mapRawUpdates(updates)
        updateDispatcher.dispatch(mappedUpdates)
    }

    private fun updateIdIfUpdatesNotEmpty(rawUpdates: List<RawUpdate>) {
        rawUpdates.lastOrNull()?.let { updateDto -> idHolder.update(updateDto.updateId) }
    }
}