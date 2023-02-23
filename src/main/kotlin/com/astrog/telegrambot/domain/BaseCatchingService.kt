package com.astrog.telegrambot.domain

import mu.KotlinLogging

private val logger = KotlinLogging.logger { }

abstract class BaseCatchingService {

    protected fun catching(onException: (exception: RuntimeException) -> Unit, block: () -> Unit) {
        try {
            block.invoke()
        } catch (ex1: RuntimeException) {
            logger.error(ex1.stackTraceToString())
            try {
                onException.invoke(ex1)
            } catch (ex2: RuntimeException) {
                logger.error(ex2.stackTraceToString())
            }
        }
    }
}