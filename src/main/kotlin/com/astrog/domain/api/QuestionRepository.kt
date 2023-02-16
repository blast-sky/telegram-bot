package com.astrog.domain.api

import com.astrog.domain.model.game.Question

interface QuestionRepository {

    fun getAllQuestions(): List<Question>
}