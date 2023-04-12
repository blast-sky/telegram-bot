package com.astrog.telegramcommon.internal.handler

import com.astrog.telegramcommon.domain.model.MessageType
import com.astrog.telegramcommon.domain.model.UpdateContent


data class MessageHandler(
    val dispatcher: MessageHandlerDispatcher,
    val handledTypes: Set<MessageType>,
) {

    fun isHandle(message: UpdateContent.Message): Boolean =
        handledTypes.contains(message.type)
}