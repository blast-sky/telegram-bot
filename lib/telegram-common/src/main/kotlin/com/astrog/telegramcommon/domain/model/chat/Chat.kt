package com.astrog.telegramcommon.domain.model.chat

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Chat(
    @JsonProperty("id")
    val id: Long,
    @JsonProperty("type")
    val type: ChatType
)
