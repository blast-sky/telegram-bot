package com.astrog.openaiapi.internal.dto.chatcompletion

import com.fasterxml.jackson.annotation.JsonProperty

enum class ChatRole {
    @JsonProperty(user)
    User,
    @JsonProperty(assistant)
    Assistant,
}

const val user = "user"
const val assistant = "assistant"
