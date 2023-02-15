package com.astrog.domain

import com.astrog.domain.api.UpdateHandler
import com.astrog.domain.model.Update
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class ProcessUpdateService(
    private val handlers: List<UpdateHandler>,
) {

    fun processUpdate(update: Update) {
        logger.info { "Start to handle update." }
        handlers.firstOrNull { handler -> handler.handle(update) }
    }
}