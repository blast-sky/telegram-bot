package com.astrog.telegramcommon.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Voice(
    @JsonProperty("file_id")
    val fileId: String,
    @JsonProperty("duration")
    val duration: Long,
)