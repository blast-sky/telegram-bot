package com.astrog.telegramcommon.api.dsl.command

import com.astrog.telegramcommon.internal.handler.CommandHandler
import com.astrog.telegramcommon.internal.handler.CommandDispatcher

fun telegramUnsupportedCommand(
    block: CommandDispatcher,
): CommandHandler {
    return telegramCommandOf(
        command = unsupportedCommandMapping,
        block = block,
    )
}

const val unsupportedCommandMapping = "TelegramUnsupportedCommandMapping$%$%"