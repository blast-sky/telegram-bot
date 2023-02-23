package com.astrog.telegrambot.api

import com.astrog.telegrambot.domain.CompletingService
import com.astrog.telegrambot.domain.ImageGenerationService
import com.astrog.telegrambot.domain.TelegramAnnouncer
import com.astrog.telegrambot.domain.questions.service.AnswerService
import com.astrog.telegrambot.domain.questions.service.NoCommandService
import com.astrog.telegrambot.domain.questions.service.StartService
import com.astrog.telegramcommon.api.TelegramCommandMapping
import com.astrog.telegramcommon.api.TelegramUnsupportedCommandMapping
import com.astrog.telegramcommon.domain.model.Message
import org.springframework.stereotype.Component

@Component
class CommandMapping(
    private val startService: StartService,
    private val answerService: AnswerService,
    private val noCommandService: NoCommandService,
    private val completingService: CompletingService,
    private val imageGenerationService: ImageGenerationService,
    private val announcer: TelegramAnnouncer,
) {

    @TelegramCommandMapping(
        command = "help",
        description = "Prints all command."
    )
    fun onHelpCommand(message: Message) {
        announcer.printHelp(message.chat.id)
    }

    @TelegramCommandMapping(
        command = "start",
        description = "Starts new game.",
    )
    fun onStartCommand(message: Message, args: String) {
        startService.process(chatId = message.chat.id)
    }

    @TelegramCommandMapping(
        command = "answer",
        description = "",
    )
    fun onAnswerCommand(message: Message, args: String) {
        answerService.process(chatId = message.chat.id, args = args)
    }

    @TelegramCommandMapping(
        command = "comp",
        description = "[text for completion] - Completion of phrase"
    )
    fun onCompletion(message: Message, args: String) {
        completingService.process(chatId = message.chat.id, args = args)
    }

    @TelegramCommandMapping(
        command = "ig",
        description = "[text for image] - Image generator."
    )
    fun onImageGeneration(message: Message, args: String) {
        imageGenerationService.process(chatId = message.chat.id, text = args)
    }

    @TelegramCommandMapping
    @TelegramUnsupportedCommandMapping
    fun onNoCommand(message: Message, args: String) {
        noCommandService.process(chatId = message.chat.id)
    }
}