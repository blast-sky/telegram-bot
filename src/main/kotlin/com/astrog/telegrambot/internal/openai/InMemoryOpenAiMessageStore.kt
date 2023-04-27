package com.astrog.telegrambot.internal.openai

import com.astrog.openaiapi.internal.dto.chatcompletion.ChatMessage
import com.astrog.telegrambot.domain.openai.OpenAiMessageStore
import com.astrog.telegrambot.inMemoryStore
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Profile(inMemoryStore)
@Service
class InMemoryOpenAiMessageStore : OpenAiMessageStore {

    private val messages = mutableMapOf<Long, MutableList<ChatMessage>>()

    override fun getMessages(id: Long): List<ChatMessage> {
        return messages.getOrDefault(id, emptyList())
    }

    override fun addMessage(id: Long, chatMessage: ChatMessage) {
        messages.merge(id, mutableListOf(chatMessage)) { old, new -> old.apply { old += new } }
    }

    override fun clearMessages(id: Long) {
        messages.remove(id)
    }
}