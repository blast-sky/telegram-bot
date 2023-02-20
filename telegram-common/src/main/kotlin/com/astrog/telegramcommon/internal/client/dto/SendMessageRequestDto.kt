package com.astrog.telegramcommon.internal.client.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class SendMessageRequestDto(
    @JsonProperty("chat_id")
    val chatId: Long,
    val text: String,
)
