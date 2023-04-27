package com.astrog.openaiapi.internal.dto.chatcompletion

import com.fasterxml.jackson.annotation.JsonProperty

enum class ChatRole(val type: String) {
    @JsonProperty("user")
    User("user"),
    @JsonProperty("assistant")
    Assistant("assistant"),
}
