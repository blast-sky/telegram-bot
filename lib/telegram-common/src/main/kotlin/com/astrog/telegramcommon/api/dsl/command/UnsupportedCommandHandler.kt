package com.astrog.telegramcommon.api.dsl.command

import com.astrog.telegramcommon.internal.handler.CommandHandlerCallback
import com.astrog.telegramcommon.internal.handler.TelegramUpdateHandler

fun telegramUnsupportedCommand(
    block: CommandHandlerCallback,
): TelegramUpdateHandler {
    return telegramCommandOf(
        handledCommand = unsupportedCommandMapping,
        block = block,
    )
}

const val unsupportedCommandMapping = "TelegramUnsupportedCommandMapping$%$%"