package com.astrog.domain.model.game

data class Question(
    val text: String,
    val wrongAnswers: List<String>,
    val rightAnswers: List<String>,
)
