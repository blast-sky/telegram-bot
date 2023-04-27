package com.astrog.telegrambot.domain

import com.astrog.openaiapi.api.OpenAiClient
import com.astrog.openaiapi.internal.dto.chatcompletion.ChatMessage
import com.astrog.openaiapi.internal.dto.chatcompletion.ChatRole
import com.astrog.telegrambot.domain.openai.OpenAiMessageStore
import com.astrog.telegramcommon.api.TelegramService
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
        onException = { ex ->
            logger.error(ex.stackTraceToString())
            telegramService.sendMessage(chatId, "Try later...")
        },
        block = {
            openAiMessageStore.addMessage(chatId, ChatMessage(ChatRole.User, args))
            val cashedMessages = openAiMessageStore.getMessages(chatId)
            val message = openAiClient.getChatCompletion(cashedMessages)
            openAiMessageStore.addMessage(chatId, message)
            telegramService.sendMessage(chatId, message.content)
        },
    )
}