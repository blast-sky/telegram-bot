package com.astrog.telegramcommon.api.dsl.command

import com.astrog.telegramcommon.domain.filter.message.CommandFilter
import com.astrog.telegramcommon.domain.model.command.extractCommand
import com.astrog.telegramcommon.domain.model.update.Message
import com.astrog.telegramcommon.internal.handler.CommandHandlerCallback
import com.astrog.telegramcommon.internal.handler.TelegramUpdateHandler

fun telegramCommandOf(
    handledCommand: String,
    block: CommandHandlerCallback,
): TelegramUpdateHandler {
    return TelegramUpdateHandler(CommandFilter(handledCommand)) { update ->
        val message = update as Message
        val command = extractCommand(message.text!!)
        block.invoke(message, command.args)
    }
}