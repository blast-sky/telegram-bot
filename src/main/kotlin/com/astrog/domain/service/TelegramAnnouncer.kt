package com.astrog.domain.service

import com.astrog.domain.api.TelegramService
import com.astrog.domain.model.game.Player
import com.astrog.domain.model.game.QuestionWithShuffledAnswers
import org.springframework.stereotype.Service

@Service
class TelegramAnnouncer(
    private val telegramService: TelegramService,
) {

    fun printMustStart(chatId: Long) {
        telegramService.sendMessage(chatId, "You are must /start to start game.")
    }

    fun printWrongAnswerFormat(chatId: Long) {
        telegramService.sendMessage(chatId, "Answer must look like this: /answer <answer number>.")
    }

    fun printWrongAnswer(chatId: Long, question: QuestionWithShuffledAnswers) {
        telegramService.sendMessage(chatId, "You are wrong. The right answer: ${question.rightAnswerIndexes}")
    }

    fun printRightAnswer(chatId: Long) {
        telegramService.sendMessage(chatId, "Good, your answer is right.")
    }

    fun printResults(chatId: Long, player: Player) {
        val percentage = player.rightAnswered.toDouble() / player.questionCount
        telegramService.sendMessage(
            chatId,
            "You are answered right on ${player.rightAnswered} question from ${player.questionCount}.\n" +
                    "Percentage: $percentage"
        )
    }

    fun askQuestion(chatId: Long, question: QuestionWithShuffledAnswers) {
        telegramService.sendMessage(chatId, question.text)
    }

    fun printGameAlreadyStarted(chatId: Long) {
        telegramService.sendMessage(chatId, "Game already started.")
    }

    fun printIDoNotUnderstand(chatId: Long) {
        telegramService.sendMessage(chatId, "I do not understand.")
    }
}