package com.astrog.telegramcommon.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

sealed class UpdateContent {

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class Message(
        @JsonProperty("message_id")
        val messageId: Long,
        @JsonProperty("chat")
        val chat: Chat,
        @JsonProperty("from")
        val from: User?,
        @JsonProperty("text")
        val text: String?,
        @JsonProperty("voice")
        val voice: Voice?,
        @JsonProperty("reply_to_message")
        val replyToMessage: Message?,
    ) : UpdateContent() {
        lateinit var type: MessageType
    }
}