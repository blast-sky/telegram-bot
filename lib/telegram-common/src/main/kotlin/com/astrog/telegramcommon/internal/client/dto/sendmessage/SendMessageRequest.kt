package com.astrog.telegramcommon.internal.client.dto.sendmessage

import com.fasterxml.jackson.annotation.JsonProperty

data class SendMessageRequest(
    @JsonProperty("chat_id")
    val chatId: Long,
    val text: String,
)
