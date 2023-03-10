package com.astrog.telegrambot.domain.questions.service

import com.astrog.telegrambot.domain.questions.api.PlayerRepository
import com.astrog.telegrambot.domain.questions.model.Player
import com.astrog.telegrambot.domain.questions.model.QuestionWithShuffledAnswers
import com.astrog.telegrambot.domain.TelegramAnnouncer
import org.springframework.stereotype.Service

@Service
class StartService(
    private val telegramAnnouncer: TelegramAnnouncer,
    private val playerRepository: PlayerRepository,
    private val questionGenerator: QuestionGenerator,
) {

    fun process(chatId: Long) {
        if (playerRepository.isPlayerInGame(chatId)) {
            telegramAnnouncer.printGameAlreadyStarted(chatId)
            return
        }

        val question = questionGenerator.generate()
        playerRepository.saveOrUpdate(constructPlayer(chatId, question))
        telegramAnnouncer.askQuestion(chatId, question)
    }

    private fun constructPlayer(userId: Long, question: QuestionWithShuffledAnswers) = Player(
        id = userId,
        rightAnswered = 0,
        wrongAnswered = 0,
        activeQuestion = question
    )
}