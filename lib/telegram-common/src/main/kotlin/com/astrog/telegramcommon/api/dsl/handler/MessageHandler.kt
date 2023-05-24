package com.astrog.telegramcommon.api.dsl.handler

import com.astrog.telegramcommon.domain.filter.UpdateFilter
import com.astrog.telegramcommon.domain.filter.message.AnyMessageFilter
import com.astrog.telegramcommon.domain.filter.message.MessageFilter
import com.astrog.telegramcommon.domain.model.update.Message
import com.astrog.telegramcommon.internal.handler.MessageHandlerCallback
import com.astrog.telegramcommon.internal.handler.TelegramUpdateHandler

fun telegramMessageHandlerOf(
    filter: UpdateFilter = AnyMessageFilter,
    block: MessageHandlerCallback,
): TelegramUpdateHandler {
    return TelegramUpdateHandler(filter) { update ->
        block.invoke(update as Message)
    }
}