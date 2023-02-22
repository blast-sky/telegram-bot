package com.astrog.openaiapi.internal.model

data class CompletingResponse(
    val choices: List<Choice>,
)

data class Choice(
    val text: String,
)