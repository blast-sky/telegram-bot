package com.astrog.telegrambot.domain.api

import com.astrog.telegrambot.domain.model.Question

interface QuestionRepository {

    fun getAllQuestions(): List<Question>
}