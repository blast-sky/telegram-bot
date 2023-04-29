package com.astrog.telegramcommon.internal.client

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class ResponseErrorDto(
    @JsonProperty("error_code")
    val errorCode: Int,
    @JsonProperty("description")
    val description: String?,
)
