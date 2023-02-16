package com.astrog.domain.service

import com.astrog.domain.model.game.Question
import com.astrog.domain.model.game.QuestionWithShuffledAnswers
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