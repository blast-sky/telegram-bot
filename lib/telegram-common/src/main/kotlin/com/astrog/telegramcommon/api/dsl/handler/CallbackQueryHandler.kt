package com.astrog.telegramcommon.api.dsl.handler

import com.astrog.telegramcommon.domain.filter.update.AnyCallbackQuery
import com.astrog.telegramcommon.domain.model.update.CallbackQuery
import com.astrog.telegramcommon.internal.handler.TelegramUpdateHandler

typealias CallbackQueryHandlerCallback = suspend (CallbackQuery) -> Unit

fun telegramCallbackHandlerOf(
    block: CallbackQueryHandlerCallback,
): TelegramUpdateHandler {
    return TelegramUpdateHandler(AnyCallbackQuery) { update ->
        block.invoke(update as CallbackQuery)
    }
}