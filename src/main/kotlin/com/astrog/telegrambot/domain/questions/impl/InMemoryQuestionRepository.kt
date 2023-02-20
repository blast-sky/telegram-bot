package com.astrog.telegrambot.domain.questions.impl

import com.astrog.telegrambot.domain.questions.api.QuestionRepository
import com.astrog.telegrambot.domain.questions.model.Question
import org.springframework.stereotype.Service

@Service
class InMemoryQuestionRepository : QuestionRepository {

    private val questions = listOf(
        Question(
            "What is the capital of France?",
            listOf("London", "Berlin", "New York"),
            listOf("Paris")
        ),
        Question(
            "What is the largest mammal on Earth?",
            listOf("Elephant", "Hippopotamus", "Rhinoceros"),
            listOf("Blue Whale")
        ),
        Question(
            "What is the currency of Japan?",
            listOf("Yuan", "Won", "Dollar"),
            listOf("Yen")
        ),
        Question(
            "What is the boiling point of water?",
            listOf("50°C", "90°C", "110°C"),
            listOf("100°C")
        ),
        Question(
            "Who was the first president of the United States?",
            listOf("Thomas Jefferson", "George Washington Carver", "Benjamin Franklin"),
            listOf("George Washington")
        )
    )

    override fun getAllQuestions(): List<Question> {
        return questions
    }
}