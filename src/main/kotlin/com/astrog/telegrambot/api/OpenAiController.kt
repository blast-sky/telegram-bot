package com.astrog.telegrambot.api

import com.astrog.telegrambot.domain.CompletingService
import com.astrog.telegrambot.domain.ImageGenerationService
import com.astrog.telegrambot.domain.TelegramAnnouncer
import com.astrog.telegrambot.domain.TranscriptionService
import com.astrog.telegrambot.domain.openai.OpenAiMessageStore
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
    private val transcriptionService: TranscriptionService,
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
        messageStore.clearMessages(message.chat.id)
        announcer.printMessageAreCleared(message.chat.id)
    }

    @TelegramMapping
    fun chatMessage() = telegramMessageHandlerOf(MessageType.MESSAGE) { message ->
        message.text?.let { text ->
            completingService.process(message.chat.id, text)
        }
    }

    @TelegramMapping
    fun transcription() = telegramMessageHandlerOf(MessageType.MESSAGE) { message ->
        message.voice?.let { voice ->
            transcriptionService.process(message.chat.id, voice.fileId)
        }
    }

    @TelegramMapping
    fun transcriptionCommand() = telegramCommandOf("tran") { message, _ ->
        message.replyToMessage?.voice?.let { voice ->
            transcriptionService.processOnlyTranscription(message.messageId, message.chat.id, voice.fileId)
        }
    }
}