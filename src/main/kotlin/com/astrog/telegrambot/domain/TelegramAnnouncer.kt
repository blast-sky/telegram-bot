package com.astrog.telegrambot.domain

import com.astrog.telegrambot.domain.questions.model.Player
import com.astrog.telegrambot.domain.questions.model.QuestionWithShuffledAnswers
import com.astrog.telegramcommon.api.TelegramService
import org.springframework.stereotype.Service

@Service
class TelegramAnnouncer(
    private val telegramService: TelegramService,
) {

    suspend fun printMessageAreCleared(chatId: Long) {
        telegramService.sendMessage(chatId, "Messages are cleared.")
    }

    suspend fun printMustStart(chatId: Long) {
        telegramService.sendMessage(chatId, "You are must /start to start game.")
    }

    suspend fun printWrongAnswerFormat(chatId: Long) {
        telegramService.sendMessage(chatId, "Answer must look like this: /answer <answer number>.")
    }

    suspend fun printWrongAnswer(chatId: Long, question: QuestionWithShuffledAnswers) {
        telegramService.sendMessage(chatId, "You are wrong. The right answer: ${question.rightAnswerIndexes}")
    }

    suspend fun printRightAnswer(chatId: Long) {
        telegramService.sendMessage(chatId, "Good, your answer is right.")
    }

    suspend fun printResults(chatId: Long, player: Player) {
        val percentage = player.rightAnswered.toDouble() / player.questionCount
        telegramService.sendMessage(
            chatId,
            "You are answered right on ${player.rightAnswered} question from ${player.questionCount}.\n" +
                "Percentage: $percentage"
        )
    }

    suspend fun askQuestion(chatId: Long, question: QuestionWithShuffledAnswers) {
        telegramService.sendMessage(chatId, question.text)
    }

    suspend fun printGameAlreadyStarted(chatId: Long) {
        telegramService.sendMessage(chatId, "Game already started.")
    }

    suspend fun printToUseHelp(chatId: Long) {
        telegramService.sendMessage(chatId, "Use /help to get supported commands.")
    }

    suspend fun printHelp(chatId: Long) {
        telegramService.sendMessage(
            chatId,
            """
            /comp [query]
            /clear
            /ig [query]
            """.trimIndent(),
        )
    }
}