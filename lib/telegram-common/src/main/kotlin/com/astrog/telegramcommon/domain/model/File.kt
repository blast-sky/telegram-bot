package com.astrog.telegramcommon.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class File(
    @JsonProperty("file_id")
    val fileId: String,
    @JsonProperty("file_unique_id")
    val fileUniqueId: String,
    @JsonProperty("file_path")
    val filePath: String?,
)
