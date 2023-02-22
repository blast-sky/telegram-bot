package com.astrog.openaiapi.internal.dto

data class CompletingResponse(
    val choices: List<Choice>,
)

data class Choice(
    val text: String,
)