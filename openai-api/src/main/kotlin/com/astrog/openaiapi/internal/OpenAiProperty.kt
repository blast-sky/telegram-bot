package com.astrog.openaiapi.internal

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("open-ai")
data class OpenAiProperty(
    val token: String,
    val baseUrl: String,
)