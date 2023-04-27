package com.astrog.telegrambot.domain

import com.astrog.openaiapi.api.OpenAiClient
import com.astrog.openaiapi.internal.dto.chatcompletion.Message
import com.astrog.telegramcommon.api.TelegramService
import org.springframework.stereotype.Service

@Service
class CompletingService(
    private val openAiClient: OpenAiClient,
    private val telegramService: TelegramService,
    private val openAiMessageStore: OpenAiMessageStore,
) : BaseCatchingService() {

    suspend fun process(chatId: Long, args: String) = catching(
        onException = { ex ->
            telegramService.sendMessage(chatId, "Try later...")
        },
        block = {
            val id = chatId.toString()
            openAiMessageStore.addMessage(id, Message("user", args))
            val cashedMessages = openAiMessageStore.getMessages(id)
            val message = openAiClient.getChatCompletion(cashedMessages)
            openAiMessageStore.addMessage(id, message)
            telegramService.sendMessage(chatId, message.content)
        },
    )
}