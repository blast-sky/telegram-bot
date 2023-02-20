package com.astrog.telegrambot.domain.model

data class Player(
    val id: Long,
    val rightAnswered: Int,
    val wrongAnswered: Int,
    val activeQuestion: QuestionWithShuffledAnswers?,
) {

    val questionCount: Int
        get() = rightAnswered + wrongAnswered
}
