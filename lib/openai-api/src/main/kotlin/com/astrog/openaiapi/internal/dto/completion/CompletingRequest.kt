package com.astrog.openaiapi.internal.dto.completion

import com.fasterxml.jackson.annotation.JsonProperty

data class CompletingRequest(
    val model: String,
    val prompt: String,
    @JsonProperty("max_tokens")
    val maxTokens: Long? = 2000,
    val temperature: Long = 0,
)