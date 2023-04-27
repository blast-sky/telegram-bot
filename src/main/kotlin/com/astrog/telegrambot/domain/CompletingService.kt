package com.astrog.telegrambot.domain

import com.astrog.openaiapi.api.OpenAiClient
import com.astrog.openaiapi.internal.dto.chatcompletion.ChatMessage
import com.astrog.telegrambot.domain.openai.OpenAiMessageStore
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
            openAiMessageStore.addMessage(chatId, ChatMessage("user", args))
            val cashedMessages = openAiMessageStore.getMessages(chatId)
            val message = openAiClient.getChatCompletion(cashedMessages)
            openAiMessageStore.addMessage(chatId, message)
            telegramService.sendMessage(chatId, message.content)
        },
    )
}