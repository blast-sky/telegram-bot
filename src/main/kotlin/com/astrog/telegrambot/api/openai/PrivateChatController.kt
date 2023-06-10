package com.astrog.telegrambot.api.openai

import com.astrog.telegrambot.domain.CompletingService
import com.astrog.telegrambot.domain.TranscriptionService
import com.astrog.telegramcommon.api.annotation.TelegramController
import com.astrog.telegramcommon.api.annotation.TelegramMapping
import com.astrog.telegramcommon.api.dsl.handler.telegramCallbackHandlerOf
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
        transcriptionService.requestTranscriptionMode(message.messageId, message.chat.id)
    }

    @TelegramMapping
    fun transcriptionCallback() = telegramCallbackHandlerOf { callbackQuery ->
        transcriptionService.choiceMode(callbackQuery)
    }
}