package com.astrog.domain.model

data class Player(
    val id: Long,
    val activeQuestion: Question?,
    val rightAnswered: Int,
    val wrongAnswered: Int,
) {

    val questionCount: Int
        get() = rightAnswered + wrongAnswered
}
