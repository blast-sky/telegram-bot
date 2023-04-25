package com.astrog.telegramcommon.internal.client

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ResponseDto<T>(
    @JsonProperty("result")
    val result: T,
    @JsonProperty("ok")
    val ok: Boolean,
)