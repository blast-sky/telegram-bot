package com.astrog.telegrambot.domain

import com.astrog.openaiapi.api.OpenAiClient
import com.astrog.telegrambot.tryLatter
import com.astrog.telegramcommon.api.TelegramService
import com.astrog.telegramcommon.domain.model.ChatAction
import org.springframework.stereotype.Service

@Service
class ImageGenerationService(
    private val openAiClient: OpenAiClient,
    private val telegramService: TelegramService,
) : BaseCatchingService() {

    suspend fun process(chatId: Long, text: String) = catching(
        onException = {
            telegramService.sendMessage(chatId, tryLatter)
        },
        block = {
            openAiClient.getImageCreation(text).forEach { imageUrl ->
                telegramService.sendChatAction(chatId, ChatAction.UPLOAD_PHOTO)
                telegramService.sendImage(chatId, imageUrl)
            }
        },
    )
}