package com.astrog.telegrambot.domain.service

import com.astrog.openaiapi.api.OpenAiClient
import com.astrog.telegrambot.tryLatter
import org.springframework.stereotype.Service

@Service
class ImageGenerationService(
    private val openAiClient: OpenAiClient,
) : BaseCatchingService() {

    suspend fun processClient(
        chatId: Long,
        text: String,
        sendStartAction: () -> Unit,
        sendImage: (String) -> Unit,
        sendText: (String) -> Unit,
    ) = catching(
        onException = {
            sendText.invoke(tryLatter)
        },
        block = {
            openAiClient.getImageCreation(text).forEach { imageUrl ->
                sendStartAction.invoke()
                sendImage(imageUrl)
            }
        },
    )
}