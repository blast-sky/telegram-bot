package com.astrog.telegramcommon.internal.client.dto.getfile

import com.fasterxml.jackson.annotation.JsonProperty

data class GetFileRequest(
    @JsonProperty("file_id")
    val fileId: String,
)