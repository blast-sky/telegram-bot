package com.astrog.domain.model.messaging

import com.fasterxml.jackson.annotation.JsonProperty

data class Message(
    @JsonProperty("message_id")
    val messageId: Long,
    val chat: Chat,
    val from: User?,
    val text: String?,
)
