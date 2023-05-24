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
class PrivateChatController(
    private val completingService: CompletingService,
    private val transcriptionService: TranscriptionService,
) {

    @TelegramMapping
    fun chatMessage() = telegramMessageHandlerOf(TextFilter and !ContainsCommandFilter) { message ->
        completingService.process(message.chat.id, message.text!!)
    }

    @TelegramMapping
    fun transcription() = telegramMessageHandlerOf(VoiceFilter) { message ->
        transcriptionService.process(message.chat.id, message.voice!!.fileId)
    }
}