package com.astrog.telegrambot.domain.model.game

data class QuestionWithShuffledAnswers(
    val rightAnswerIndexes: List<Int>,
    val text: String,
)

