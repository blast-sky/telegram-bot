package com.astrog.telegramcommon.api.dsl.command

import com.astrog.telegramcommon.internal.handler.CommandHandler
import com.astrog.telegramcommon.internal.handler.CommandDispatcher

fun telegramCommandOf(
    command: String,
    description: String = "",
    block: CommandDispatcher,
): CommandHandler {
    return CommandHandler(command, description, block)
}