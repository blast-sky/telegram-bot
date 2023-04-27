package com.astrog.telegrambot.internal.openai

import com.astrog.openaiapi.internal.dto.chatcompletion.ChatMessage
import com.astrog.telegrambot.domain.openai.OpenAiMessageStore
import org.springframework.stereotype.Service

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