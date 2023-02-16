package com.astrog.domain.service

import com.astrog.domain.api.QuestionRepository
import com.astrog.domain.model.game.QuestionWithShuffledAnswers
import org.springframework.stereotype.Service

@Service
class QuestionGenerator(
    private val questionRepository: QuestionRepository,
    private val questionFormatter: QuestionFormatter,
) {

    fun generate(): QuestionWithShuffledAnswers {
        val question = questionRepository.getAllQuestions().random()
        return questionFormatter.formatToQuestionWithShuffledAnswers(question)
    }
}