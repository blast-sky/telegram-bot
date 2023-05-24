package com.astrog.telegrambot.domain

import com.astrog.openaiapi.api.OpenAiClient
import com.astrog.openaiapi.internal.dto.chatcompletion.ChatMessage
import com.astrog.openaiapi.internal.dto.chatcompletion.ChatRole
import com.astrog.telegrambot.domain.openai.OpenAiMessageStore
import com.astrog.telegramcommon.api.TelegramService
import com.astrog.telegramcommon.domain.model.ChatAction
import com.astrog.telegramcommon.domain.model.MessageParseMode
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger { }

@Service
class CompletingService(
    private val openAiClient: OpenAiClient,
    private val telegramService: TelegramService,
    private val openAiMessageStore: OpenAiMessageStore,
) : BaseCatchingService() {

    suspend fun process(chatId: Long, args: String) = catching(
        onException = {
            telegramService.sendMessage(chatId, "Try later...")
        },
        block = {
            telegramService.sendChatAction(chatId, ChatAction.TYPING)
            val completion = getChatCompletionAndStoreMessages(chatId, args)
            telegramService.sendMessage(chatId, completion, parseMode = MessageParseMode.MARKDOWN)
        },
    )

    suspend fun getChatCompletionAndStoreMessages(chatId: Long, request: String): String {
        val currentMessage = ChatMessage(ChatRole.User, request)

        var totalMessageLength = 0
        val cashedMessages = openAiMessageStore.getMessages(chatId)
            .takeLastWhile {
                totalMessageLength += it.content.length
                totalMessageLength < OpenAiClient.maxTokenCount
            }

        val modelResponse = openAiClient.getChatCompletion(cashedMessages + currentMessage)

        openAiMessageStore.addMessage(chatId, currentMessage)
        openAiMessageStore.addMessage(chatId, modelResponse)
        return modelResponse.content
    }
}