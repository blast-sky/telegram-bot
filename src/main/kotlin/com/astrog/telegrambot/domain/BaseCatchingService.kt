package com.astrog.telegrambot.domain

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import mu.KotlinLogging

private val logger = KotlinLogging.logger { }

abstract class BaseCatchingService {

    private val exceptionLogger = CoroutineExceptionHandler { _, throwable ->
        logger.error(throwable.stackTraceToString())
    }

    private val scope = CoroutineScope(SupervisorJob() + exceptionLogger)

    protected suspend fun catching(
        onException: suspend (exception: RuntimeException) -> Unit,
        block: suspend () -> Unit,
    ) {
        with(scope) {
            val mainJob = async { block.invoke() }
            try {
                mainJob.await()
            } catch (ex: RuntimeException) {
                logger.error(ex.stackTraceToString())
                onException.invoke(ex)
            }
        }
    }
}