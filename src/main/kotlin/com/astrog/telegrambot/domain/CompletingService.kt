package com.astrog.telegrambot.domain

import com.astrog.openaiapi.api.OpenAiClient
import com.astrog.telegramcommon.api.TelegramService
import org.springframework.stereotype.Service

@Service
class CompletingService(
    private val openAiClient: OpenAiClient,
    private val telegramService: TelegramService,
) {

    fun process(chatId: Long, args: String) {
        val completing = openAiClient.requestComplete(args)
        telegramService.sendMessage(chatId, completing)
    }
}