package com.astrog.telegramcommon.internal.client.dto.sendphoto

import com.fasterxml.jackson.annotation.JsonProperty

data class SendPhotoRequest(
    @JsonProperty("chat_id")
    val chaId: Long,
    val photo: String,
)