package com.astrog.telegrambot.api.openai

import com.astrog.telegrambot.domain.CompletingService
import com.astrog.telegrambot.domain.ImageGenerationService
import com.astrog.telegrambot.domain.TelegramAnnouncer
import com.astrog.telegrambot.domain.TranscriptionService
import com.astrog.telegrambot.domain.openai.OpenAiMessageStore
import com.astrog.telegramcommon.api.annotation.TelegramController
import com.astrog.telegramcommon.api.annotation.TelegramMapping
import com.astrog.telegramcommon.api.dsl.command.telegramCommandOf
import com.astrog.telegramcommon.api.dsl.handler.telegramMessageHandlerOf
import com.astrog.telegramcommon.domain.filter.message.ContainsCommandFilter
import com.astrog.telegramcommon.domain.filter.message.TextFilter
import com.astrog.telegramcommon.domain.filter.message.VoiceFilter

@TelegramController
class CommonOpenAiController(
    private val messageStore: OpenAiMessageStore,
    private val completingService: CompletingService,
    private val imageGenerationService: ImageGenerationService,
    private val announcer: TelegramAnnouncer,
    private val transcriptionService: TranscriptionService,
) {

    @TelegramMapping
    fun completionCommand() = telegramCommandOf(handledCommand = "comp") { message, args ->
        completingService.process(message.chat.id, args)
    }

    @TelegramMapping
    fun onImageGeneration() = telegramCommandOf(handledCommand = "ig") { message, args ->
        imageGenerationService.process(message.chat.id, args)
    }

    @TelegramMapping
    fun clearCommand() = telegramCommandOf(handledCommand = "clear") { message, args ->
        if (args.isNotBlank()) {
            return@telegramCommandOf announcer.printHelp(message.chat.id)
        }
        messageStore.clearMessages(message.chat.id)
        announcer.printMessageAreCleared(message.chat.id)
    }

    @TelegramMapping
    fun transcriptionCommand() = telegramCommandOf(handledCommand = "tran") { message, _ ->
        message.replyToMessage?.voice?.let { voice ->
            transcriptionService.processOnlyTranscription(message.messageId, message.chat.id, voice.fileId)
        }
    }
}