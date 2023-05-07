package com.astrog.telegramcommon.domain.model.chat

import com.fasterxml.jackson.annotation.JsonProperty

enum class ChatType {
    @JsonProperty("private")
    PRIVATE,
    @JsonProperty("group")
    GROUP,
    @JsonProperty("supergroup")
    SUPERGROUP
}
