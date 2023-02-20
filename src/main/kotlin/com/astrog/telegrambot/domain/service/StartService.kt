package com.astrog.telegrambot.domain.service

import com.astrog.telegrambot.domain.api.PlayerRepository
import com.astrog.telegrambot.domain.model.Player
import com.astrog.telegrambot.domain.model.QuestionWithShuffledAnswers
import com.astrog.telegrambot.internal.TelegramAnnouncer
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