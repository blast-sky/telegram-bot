package com.astrog.telegrambot.api

import com.astrog.telegrambot.domain.CompletingService
import com.astrog.telegrambot.domain.ImageGenerationService
import com.astrog.telegrambot.domain.questions.service.AnswerService
import com.astrog.telegrambot.domain.questions.service.NoCommandService
import com.astrog.telegrambot.domain.questions.service.StartService
import com.astrog.telegramcommon.api.TelegramCommandMapping
import com.astrog.telegramcommon.domain.model.Message
import org.springframework.stereotype.Component

@Component
class CommandMapping(
    private val startService: StartService,
    private val answerService: AnswerService,
    private val noCommandService: NoCommandService,
    private val completingService: CompletingService,
    private val imageGenerationService: ImageGenerationService,
) {

    @TelegramCommandMapping("start")
    fun onStartCommand(message: Message, args: String) {
        startService.process(chatId = message.chat.id)
    }

    @TelegramCommandMapping("answer")
    fun onAnswerCommand(message: Message, args: String) {
        answerService.process(chatId = message.chat.id, args = args)
    }

    @TelegramCommandMapping("comp")
    fun onCompletion(message: Message, args: String) {
        completingService.process(chatId = message.chat.id, args = args)
    }

    @TelegramCommandMapping("ig")
    fun onImageGeneration(message: Message, args: String) {
        imageGenerationService.process(chatId = message.chat.id, text = args)
    }

    @TelegramCommandMapping
    fun onNoCommand(message: Message, args: String) {
        noCommandService.process(chatId = message.chat.id)
    }
}