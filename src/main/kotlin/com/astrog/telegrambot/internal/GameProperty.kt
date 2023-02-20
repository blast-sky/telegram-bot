package com.astrog.telegrambot.internal

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "game")
data class GameProperty(
    val gameQuestionCont: Int,
)
