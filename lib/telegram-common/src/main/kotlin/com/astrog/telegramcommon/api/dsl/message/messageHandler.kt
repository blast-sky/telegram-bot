package com.astrog.telegramcommon.api.dsl.message

import com.astrog.telegramcommon.internal.handler.MessageHandler
import com.astrog.telegramcommon.internal.handler.MessageHandlerDispatcher

fun telegramMessageHandlerOf(block: MessageHandlerDispatcher): MessageHandler {
    return MessageHandler(block)
}