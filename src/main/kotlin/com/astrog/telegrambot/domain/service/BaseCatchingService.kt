package com.astrog.telegrambot.domain.service

import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import mu.KotlinLogging

private val logger = KotlinLogging.logger { }

abstract class BaseCatchingService {

    protected suspend fun catching(
        onException: suspend (exception: Exception) -> Unit,
        block: suspend () -> Unit,
    ) {
        try {
            coroutineScope { block.invoke() }
        } catch (exceptionInBlock: Exception) {
            withContext(NonCancellable) {
                logger.error(exceptionInBlock.stackTraceToString())
                try {
                    coroutineScope { onException.invoke(exceptionInBlock) }
                } catch (exceptionInOnException: Exception) {
                    logger.error(exceptionInOnException.stackTraceToString())
                }
            }
        }
    }
}