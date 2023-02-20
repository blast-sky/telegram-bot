package com.astrog.telegrambot.domain.api

import com.astrog.telegrambot.domain.model.game.Question

interface QuestionRepository {

    fun getAllQuestions(): List<Question>
}