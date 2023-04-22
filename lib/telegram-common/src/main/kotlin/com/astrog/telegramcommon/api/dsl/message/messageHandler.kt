package com.astrog.telegramcommon.api.dsl.message

import com.astrog.telegramcommon.domain.model.MessageType
import com.astrog.telegramcommon.internal.handler.MessageHandler
import com.astrog.telegramcommon.internal.handler.MessageHandlerDispatcher

fun telegramMessageHandlerOf(
    handledMessageTypes: Set<MessageType>,
    block: MessageHandlerDispatcher,
): MessageHandler {
    return MessageHandler(block, handledMessageTypes)
}

fun telegramMessageHandlerOf(
    handledMessageType: MessageType,
    block: MessageHandlerDispatcher,
): MessageHandler {
    return MessageHandler(block, setOf(handledMessageType))
}