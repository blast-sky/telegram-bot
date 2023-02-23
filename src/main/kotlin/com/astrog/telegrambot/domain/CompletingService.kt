package com.astrog.telegrambot.domain

import com.astrog.openaiapi.api.OpenAiClient
import com.astrog.telegramcommon.api.TelegramService
import org.springframework.stereotype.Service

@Service
class CompletingService(
    private val openAiClient: OpenAiClient,
    private val telegramService: TelegramService,
) : BaseCatchingService() {

    fun process(chatId: Long, args: String) = catching(
        onException = {
            telegramService.sendMessage(chatId, "Try later...")
        },
        block = {
            val completing = openAiClient.getCompletion(args)
            telegramService.sendMessage(chatId, completing)
        },
    )
}