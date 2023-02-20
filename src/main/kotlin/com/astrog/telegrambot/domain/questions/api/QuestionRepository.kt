package com.astrog.telegrambot.domain.questions.api

import com.astrog.telegrambot.domain.questions.model.Question

interface QuestionRepository {

    fun getAllQuestions(): List<Question>
}