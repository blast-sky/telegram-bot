package com.astrog.telegramcommon.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class MessageParseMode {
    @JsonProperty("MarkdownV2")
    MarkdownV2,
    @JsonProperty("HTML")
    HTML,
}