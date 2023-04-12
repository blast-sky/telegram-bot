package com.astrog.telegrambot.api

import com.astrog.telegrambot.domain.CompletingService
import com.astrog.telegrambot.domain.ImageGenerationService
import com.astrog.telegrambot.domain.OpenAiMessageStore
import com.astrog.telegrambot.domain.TelegramAnnouncer
import com.astrog.telegramcommon.api.TelegramService
import com.astrog.telegramcommon.api.annotation.TelegramController
import com.astrog.telegramcommon.api.annotation.TelegramMapping
import com.astrog.telegramcommon.api.dsl.command.telegramCommandOf
import com.astrog.telegramcommon.api.dsl.message.telegramMessageHandlerOf
import com.astrog.telegramcommon.domain.model.MessageType
import mu.KotlinLogging

private val logger = KotlinLogging.logger { }

@TelegramController
class OpenAiController(
    private val messageStore: OpenAiMessageStore,
    private val completingService: CompletingService,
    private val imageGenerationService: ImageGenerationService,
    private val telegramService: TelegramService,
    private val announcer: TelegramAnnouncer,
) {

    @TelegramMapping
    fun completionCommand() = telegramCommandOf(command = "comp") { message, args ->
        completingService.process(message.chat.id, args)
    }

    @TelegramMapping
    fun onImageGeneration() = telegramCommandOf(command = "ig") { message, args ->
        imageGenerationService.process(message.chat.id, args)
    }

    @TelegramMapping
    fun clearCommand() = telegramCommandOf(command = "clear") { message, args ->
        if (args.isNotBlank()) {
            return@telegramCommandOf announcer.printHelp(message.chat.id)
        }
        messageStore.clearMessages(message.chat.id.toString())
        announcer.printMessageAreCleared(message.chat.id)
    }

    @TelegramMapping
    fun downloadVoice() = telegramMessageHandlerOf(MessageType.MESSAGE) { message ->
        message.voice?.let { voice ->
            val fileName = telegramService.downloadFile(voice.fileId)
            logger.info { "File downloaded: $fileName" }
        }
    }
}