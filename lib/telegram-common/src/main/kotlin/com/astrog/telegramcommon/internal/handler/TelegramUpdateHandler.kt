package com.astrog.telegramcommon.internal.handler

import com.astrog.telegramcommon.domain.filter.UpdateFilter
import com.astrog.telegramcommon.domain.model.update.Message
import com.astrog.telegramcommon.domain.model.update.Update

typealias UpdateHandlerCallback = suspend (Update) -> Unit
typealias MessageHandlerCallback = suspend (message: Message) -> Unit
typealias CommandHandlerCallback = suspend (message: Message, args: String) -> Unit

data class TelegramUpdateHandler(
    private val filter: UpdateFilter,
    private val dispatcher: UpdateHandlerCallback,
) {

    fun isSatisfy(update: Update): Boolean = filter.isSatisfy(update)

    suspend fun dispatch(update: Update) = dispatcher.invoke(update)
}