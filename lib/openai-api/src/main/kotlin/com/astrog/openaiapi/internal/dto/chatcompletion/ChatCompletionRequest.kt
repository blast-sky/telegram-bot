package com.astrog.openaiapi.internal.dto.chatcompletion

data class ChatCompletionRequest(
    val model: String,
    val chatMessages: List<ChatMessage>,
)

data class ChatMessage(
    val role: ChatRole,
    val content: String,
)
