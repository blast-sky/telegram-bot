package com.astrog.internal.property

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "telegram")
data class TelegramBotProperty constructor(
    private val botToken: String,
    private val baseUrl: String,
    val longPollingTimeout: Int,
) {

    val baseUrlWithToken: String
        get() = "$baseUrl/bot$botToken"
}