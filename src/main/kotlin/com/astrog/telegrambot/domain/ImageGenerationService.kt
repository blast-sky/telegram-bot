package com.astrog.telegrambot.domain

import com.astrog.openaiapi.api.OpenAiClient
import com.astrog.telegramcommon.api.TelegramService
import org.springframework.stereotype.Service

@Service
class ImageGenerationService(
    private val openAiClient: OpenAiClient,
    private val telegramService: TelegramService,
) : BaseCatchingService() {

    fun process(chatId: Long, text: String) = catching(
        onException = {
            telegramService.sendMessage(chatId, "Try later...")
        },
        block = {
            openAiClient.getImageCreation(text).forEach { imageUrl ->
                telegramService.sendImage(chatId, imageUrl)
            }
        },
    )
}