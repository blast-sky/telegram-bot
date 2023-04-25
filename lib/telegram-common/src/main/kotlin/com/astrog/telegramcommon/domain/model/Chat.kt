package com.astrog.telegramcommon.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Chat(
    @JsonProperty("id")
    val id: Long,
)
