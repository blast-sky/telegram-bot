package com.astrog.telegramcommon.internal.runner

import com.astrog.telegramcommon.api.dsl.command.unsupportedCommandMapping
import com.astrog.telegramcommon.domain.model.Update
import com.astrog.telegramcommon.domain.model.UpdateContent
import com.astrog.telegramcommon.internal.handler.CommandHandler
import com.astrog.telegramcommon.internal.handler.MessageHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger { }

@Service
class UpdateDispatcher(
    _commandHandlers: List<CommandHandler>,
    private val messageHandlers: List<MessageHandler>,
) {

    private val commands = _commandHandlers.groupBy(CommandHandler::command)

    private val dispatcherScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    suspend fun dispatch(updates: List<Update>) = updates.forEach { update ->
        logger.info { "Process update: $update." }
        dispatcherScope.launch {
            update.messageWithType?.let { message ->
                processMessage(message)
            }
        }
    }

    private suspend fun processMessage(message: UpdateContent.Message) {
        message.text?.extractCommand()?.let { (command, args) ->
            if (command.isNotBlank()) {
                return processCommand(message, command, args)
            }
        }

        messageHandlers.forEach { handler ->
            if (handler.isHandle(message)) {
                handler.dispatcher.invoke(message)
            }
        }
    }

    private suspend fun processCommand(message: UpdateContent.Message, command: String, args: String) {
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