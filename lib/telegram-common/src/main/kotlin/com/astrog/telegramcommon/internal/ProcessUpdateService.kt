package com.astrog.telegramcommon.internal

import com.astrog.telegramcommon.domain.model.Update
import com.astrog.telegramcommon.internal.mapping.TelegramCommandMappingCollector
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class ProcessUpdateService(
    private val commandCollector: TelegramCommandMappingCollector,
) {

    fun processUpdate(update: Update) {
        logger.info { "Start to process update $update." }
        update.message?.text?.let { text ->
            val commandAndArgs = extractCommand(text)
            commandCollector
                .commands[commandAndArgs.first]
                ?.forEach { invocation ->
                    invocation.invoke(update.message, commandAndArgs.second)
                }
        }
    }

    private fun extractCommand(text: String): Pair<String, String> {
        val regex = "^/(\\w+).*".toRegex()
        val match = regex.matchEntire(text)
        val command = match?.groups?.get(1)?.value ?: ""
        return command to text.substringAfter(command).removePrefix(" ")
    }
}