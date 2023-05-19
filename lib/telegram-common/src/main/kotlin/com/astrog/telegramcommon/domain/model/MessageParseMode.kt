package com.astrog.telegramcommon.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class MessageParseMode {
    @JsonProperty("MarkdownV2")
    MARKDOWNV2,

    @JsonProperty("Markdown")
    MARKDOWN,

    @JsonProperty("HTML")
    HTML,
}