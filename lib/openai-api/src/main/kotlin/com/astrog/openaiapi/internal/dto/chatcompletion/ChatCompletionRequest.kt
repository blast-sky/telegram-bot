package com.astrog.openaiapi.internal.dto.chatcompletion

import com.fasterxml.jackson.annotation.JsonProperty

data class ChatCompletionRequest(
    val model: String,
    @JsonProperty("messages")
    val messages: List<ChatMessage>,
)

data class ChatMessage(
    val role: ChatRole,
    val content: String,
)
