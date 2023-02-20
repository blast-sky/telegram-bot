package com.astrog.telegrambot.domain

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "game")
data class GameProperty(
    val gameQuestionCont: Int,
)
