package com.astrog.openaiapi.internal.model

import com.fasterxml.jackson.annotation.JsonProperty

data class CompletingRequest(
    val model: String = "text-davinci-003",
    val prompt: String,
    @JsonProperty("max_tokens")
    val maxTokens: Long = 2048,
    val temperature: Long = 0,
)