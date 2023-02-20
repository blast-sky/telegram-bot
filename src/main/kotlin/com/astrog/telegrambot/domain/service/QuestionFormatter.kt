package com.astrog.telegrambot.domain.service

import com.astrog.telegrambot.domain.model.Question
import com.astrog.telegrambot.domain.model.QuestionWithShuffledAnswers
import org.springframework.stereotype.Service

@Service
class QuestionFormatter {

    fun formatToQuestionWithShuffledAnswers(question: Question): QuestionWithShuffledAnswers {
        question.apply {
            val shuffledAnswers = (wrongAnswers + rightAnswers).shuffled()
            val rightAnswerIndexes = shuffledAnswers.mapIndexedNotNull { index, answer ->
                return@mapIndexedNotNull if (answer in rightAnswers) index else null
            }
            val answersString = shuffledAnswers
                .mapIndexed { index, answer -> "$index) $answer" }
                .joinToString(separator = "\n")
            val text = "Вопрос: ${text}\nВарианты ответа:\n\n$answersString"
            return QuestionWithShuffledAnswers(rightAnswerIndexes, text)
        }
    }
}