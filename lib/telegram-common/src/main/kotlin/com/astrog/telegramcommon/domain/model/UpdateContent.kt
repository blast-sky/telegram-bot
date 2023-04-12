package com.astrog.telegramcommon.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

sealed class UpdateContent {

    data class Message(
        @JsonProperty("message_id")
        val messageId: Long,
        val chat: Chat,
        val from: User?,
        val text: String?,
        val voice: Voice?,
        @JsonProperty("reply_to_message")
        val replyToMessage: Message?,
    ) : UpdateContent() {
        lateinit var type: MessageType
    }
}