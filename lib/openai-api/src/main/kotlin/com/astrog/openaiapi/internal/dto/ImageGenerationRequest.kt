package com.astrog.openaiapi.internal.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class ImageGenerationRequest(
    val prompt: String,
    @JsonProperty("n")
    val imageCount: Int = 1,
    val size: String = "1024x1024",
)