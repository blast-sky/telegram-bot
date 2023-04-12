package com.astrog.openaiapi.internal.dto.chatcompletion

import com.fasterxml.jackson.annotation.JsonProperty

data class ChatCompletionResponse(
    val choices: List<Choice>,
)

data class Choice(
    val message: Message,
    @JsonProperty("finish_reason")
    val finishReason: String,
)
