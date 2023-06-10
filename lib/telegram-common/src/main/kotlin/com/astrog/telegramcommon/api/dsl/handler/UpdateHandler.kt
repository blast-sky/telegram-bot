package com.astrog.telegramcommon.api.dsl.handler

import com.astrog.telegramcommon.domain.filter.UpdateFilter
import com.astrog.telegramcommon.domain.filter.update.AnyUpdate
import com.astrog.telegramcommon.internal.handler.TelegramUpdateHandler
import com.astrog.telegramcommon.internal.handler.UpdateHandlerCallback

fun telegramUpdateHandlerOf(
    filter: UpdateFilter = AnyUpdate,
    block: UpdateHandlerCallback,
): TelegramUpdateHandler {
    return TelegramUpdateHandler(filter, block)
}