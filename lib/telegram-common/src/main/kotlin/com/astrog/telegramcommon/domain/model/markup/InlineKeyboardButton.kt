package com.astrog.telegramcommon.domain.model.markup

import com.fasterxml.jackson.annotation.JsonProperty

data class InlineKeyboardButton(
    val text: String,
    @JsonProperty("callback_data")
    val callbackData: String?,
)
