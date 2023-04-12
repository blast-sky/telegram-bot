package com.astrog.telegramcommon.internal.runner

import com.astrog.telegramcommon.api.dsl.command.unsupportedCommandMapping
import com.astrog.telegramcommon.domain.model.Update
import com.astrog.telegramcommon.domain.model.UpdateContent
import com.astrog.telegramcommon.internal.handler.CommandHandler
import com.astrog.telegramcommon.internal.handler.MessageHandler
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class UpdateProcessor(
    _commandHandlers: List<CommandHandler>,
    private val messageHandlers: List<MessageHandler>,
) {

    private val commands = _commandHandlers.groupBy(CommandHandler::command)

    fun process(update: Update) {
        logger.info { "Process update: $update." }
        when (val baseMessage = update.firstNotNullUpdateContent) {
            is UpdateContent.Message -> processMessage(baseMessage) // TODO add other UpdateContent types
            else -> processBaseMessage(baseMessage)
        }
    }

    private fun processMessage(message: UpdateContent.Message) {
        message.text?.extractCommand()?.let { (command, args) ->
            if (command.isNotBlank()) {
                return processCommand(message, command, args)
            }
        }

        processBaseMessage(message)
    }

    private fun processBaseMessage(message: UpdateContent.BaseMessage) {
        messageHandlers.forEach { handler ->
            handler.dispatcher.invoke(message)
        }
    }

    private fun processCommand(message: UpdateContent.Message, command: String, args: String) {
        val acceptedCommands = commands[command]
            ?: commands[unsupportedCommandMapping]
            ?: emptyList()

        if (acceptedCommands.isEmpty()) {
            return logger.info { "No one command supported for message: $message" }
        }

        acceptedCommands.forEach { commandModel ->
            commandModel.dispatcher.invoke(message, args)
        }
    }
}

private fun String.extractCommand(): Pair<String, String> {
    val regex = "^/(\\w+).*".toRegex()
    val match = regex.matchEntire(this)
    val command = match?.groups?.get(1)?.value ?: ""
    return command to substringAfter(command).removePrefix(" ")
}
