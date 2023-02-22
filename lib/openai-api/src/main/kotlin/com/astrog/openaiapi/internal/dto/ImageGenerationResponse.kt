package com.astrog.openaiapi.internal.dto

data class ImageGenerationResponse(
    val created: Long,
    val data: List<Data>,
)

data class Data(
    val url: String,
)
