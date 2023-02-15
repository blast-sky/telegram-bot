package com.astrog.domain.model

data class Question(
    val text: String,
    val wrongAnswers: List<String>,
    val rightAnswers: List<String>,
)
