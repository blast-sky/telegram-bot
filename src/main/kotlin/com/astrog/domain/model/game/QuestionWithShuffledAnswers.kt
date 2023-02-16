package com.astrog.domain.model.game

data class QuestionWithShuffledAnswers(
    val rightAnswerIndexes: List<Int>,
    val text: String,
)

