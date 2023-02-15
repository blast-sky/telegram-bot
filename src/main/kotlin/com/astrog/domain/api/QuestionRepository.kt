package com.astrog.domain.api

import com.astrog.domain.model.Question

interface QuestionRepository {

    fun getAllQuestions(): List<Question>
}