package com.astrog.telegrambot.domain.service

import com.astrog.openaiapi.api.OpenAiClient
import com.astrog.openaiapi.internal.dto.chatcompletion.ChatMessage
import com.astrog.openaiapi.internal.dto.chatcompletion.ChatRole
import com.astrog.telegrambot.domain.openai.OpenAiMessageStore
import com.astrog.telegrambot.tryLatter
import org.springframework.stereotype.Service

@Service
class CompletingService(
    private val openAiClient: OpenAiClient,
    private val openAiMessageStore: OpenAiMessageStore,
) : BaseCatchingService() {

    suspend fun processClient(
        id: Long,
        args: String,
        sendTypingAction: () -> Unit,
        sendResult: (String) -> Unit,
    ) = catching(
        onException = {
            sendResult.invoke(tryLatter)
        },
        block = {
            sendTypingAction.invoke()
            val completion = getChatCompletionAndStoreMessages(id, args)
            sendResult.invoke(completion)
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