package com.astrog.telegramcommon.internal

import com.astrog.telegramcommon.api.annotation.unsupportedCommandMapping
import com.astrog.telegramcommon.domain.model.Update
import com.astrog.telegramcommon.internal.mapping.Command
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class ProcessUpdateService(
    private val commandsList: List<Command>,
) {

    private val commands by lazy { commandsList.groupBy(Command::command) }

    fun processUpdate(update: Update) {
        logger.info { "Process update: $update." }
        update.message?.text?.let { text ->
            val commandAndArgs = extractCommand(text)
            val acceptedCommands = commands[commandAndArgs.first]
                ?: commands[unsupportedCommandMapping]
                ?: emptyList()
            if (acceptedCommands.isEmpty()) {
                logger.info { "No one command supported for update: $update" }
            } else {
                acceptedCommands.forEach { command ->
                    command.method.invoke(update.message, commandAndArgs.second)
                }
            }
        } ?: logger.info { "There have not message text for update: $update" }
    }
}

private fun extractCommand(text: String): Pair<String, String> {
    val regex = "^/(\\w+).*".toRegex()
    val match = regex.matchEntire(text)
    val command = match?.groups?.get(1)?.value ?: ""
    return command to text.substringAfter(command).removePrefix(" ")
}
