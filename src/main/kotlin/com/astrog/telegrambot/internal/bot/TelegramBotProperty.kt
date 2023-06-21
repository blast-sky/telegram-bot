package com.astrog.telegrambot.internal.bot

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("telegram")
data class TelegramBotProperty(
    val botToken: String,
    val longPollingTimeout: Int,
)