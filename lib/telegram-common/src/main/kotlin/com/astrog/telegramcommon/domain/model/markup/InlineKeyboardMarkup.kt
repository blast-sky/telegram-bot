package com.astrog.telegramcommon.domain.model.markup

import com.fasterxml.jackson.annotation.JsonProperty

data class InlineKeyboardMarkup(
    @JsonProperty("inline_keyboard")
    val inlineKeyboard: List<List<InlineKeyboardButton>>,
)
