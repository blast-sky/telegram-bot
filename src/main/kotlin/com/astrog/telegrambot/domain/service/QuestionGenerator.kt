package com.astrog.telegrambot.domain.service

import com.astrog.telegrambot.domain.api.QuestionRepository
import com.astrog.telegrambot.domain.model.game.QuestionWithShuffledAnswers
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