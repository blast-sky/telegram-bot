package com.astrog.telegrambot.domain.questions.model

data class Question(
    val text: String,
    val wrongAnswers: List<String>,
    val rightAnswers: List<String>,
)
