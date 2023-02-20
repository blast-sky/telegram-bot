package com.astrog.telegrambot.domain.questions.service

import com.astrog.telegrambot.domain.questions.api.PlayerRepository
import com.astrog.telegrambot.domain.questions.model.Player
import com.astrog.telegrambot.internal.GameProperty
import com.astrog.telegrambot.internal.TelegramAnnouncer
import org.springframework.stereotype.Service

@Service
class AnswerService(
    private val playerRepository: PlayerRepository,
    private val telegramAnnouncer: TelegramAnnouncer,
    private val questionGenerator: QuestionGenerator,
    private val gameProperty: GameProperty,
) {

    fun process(chatId: Long, args: String) {
        if (!playerRepository.isPlayerInGame(chatId)) {
            telegramAnnouncer.printMustStart(chatId)
            return
        }
        val castedAnswer = args.toIntOrNull()
        if (castedAnswer == null) {
            telegramAnnouncer.printWrongAnswerFormat(chatId)
            return
        }
        val player = playerRepository.findPlayerById(chatId)

        val isRightAnswer = castedAnswer in player.activeQuestion!!.rightAnswerIndexes
        if (isRightAnswer)
            telegramAnnouncer.printRightAnswer(chatId)
        else
            telegramAnnouncer.printWrongAnswer(chatId, player.activeQuestion)

        val isGameEnded = player.questionCount + 1 >= gameProperty.gameQuestionCont
        val updatedPlayer = updatePlayer(isGameEnded, isRightAnswer, player)
        if (isGameEnded)
            telegramAnnouncer.printResults(chatId, updatedPlayer)
        else
            telegramAnnouncer.askQuestion(chatId, updatedPlayer.activeQuestion!!)
    }

    private fun updatePlayer(isGameEnded: Boolean, isRightAnswers: Boolean, player: Player): Player {
        val playerWithUpdatedQuestionCounter = if (isRightAnswers)
            player.copy(rightAnswered = player.rightAnswered + 1)
        else
            player.copy(wrongAnswered = player.wrongAnswered + 1)

        val playerWithUpdatedActiveQuestion = if (isGameEnded)
            playerWithUpdatedQuestionCounter.copy(activeQuestion = null)
        else
            playerWithUpdatedQuestionCounter.copy(activeQuestion = questionGenerator.generate())

        playerRepository.saveOrUpdate(playerWithUpdatedActiveQuestion)
        return playerWithUpdatedActiveQuestion
    }
}