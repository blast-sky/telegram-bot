package com.astrog.telegrambot.domain.questions.model

data class QuestionWithShuffledAnswers(
    val rightAnswerIndexes: List<Int>,
    val text: String,
)

