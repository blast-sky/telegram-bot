package com.astrog.telegrambot.api

import com.astrog.telegrambot.domain.CompletingService
import com.astrog.telegrambot.domain.ImageGenerationService
import com.astrog.telegramcommon.api.annotation.TelegramController
import com.astrog.telegramcommon.api.annotation.TelegramMapping
import com.astrog.telegramcommon.api.dsl.command.telegramCommandOf

@TelegramController
class OpenAiController(
    private val completingService: CompletingService,
    private val imageGenerationService: ImageGenerationService,
) {

    @TelegramMapping
    fun completionCommand() = telegramCommandOf(command = "comp") { message, args ->
        completingService.process(message.chat.id, args)
    }

    @TelegramMapping
    fun onImageGeneration() = telegramCommandOf(command = "ig") { message, args ->
        imageGenerationService.process(message.chat.id, args)
    }
}