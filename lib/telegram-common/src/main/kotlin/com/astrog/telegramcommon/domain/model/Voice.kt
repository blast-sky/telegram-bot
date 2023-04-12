package com.astrog.telegramcommon.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Voice(
    @JsonProperty("file_id")
    val fileId: String,
    val duration: Long,
)