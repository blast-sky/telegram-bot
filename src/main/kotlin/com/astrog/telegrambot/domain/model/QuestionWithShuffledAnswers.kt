package com.astrog.telegrambot.domain.model

data class QuestionWithShuffledAnswers(
    val rightAnswerIndexes: List<Int>,
    val text: String,
)

