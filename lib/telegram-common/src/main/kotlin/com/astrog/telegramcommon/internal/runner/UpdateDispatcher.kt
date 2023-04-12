package com.astrog.telegramcommon.internal.runner

import com.astrog.telegramcommon.domain.model.Update
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.util.concurrent.Executors

private val logger = KotlinLogging.logger { }

@Service
class UpdateDispatcher(
    private val updateProcessor: UpdateProcessor,
) {

    private val executor = Executors.newFixedThreadPool(16) // TODO move to configuration

    fun dispatch(update: Update) {
        executor.submit {
            try {
                updateProcessor.process(update)
            } catch (ex: Exception) {
                logger.error(ex.stackTraceToString())
            }
        }
    }
}