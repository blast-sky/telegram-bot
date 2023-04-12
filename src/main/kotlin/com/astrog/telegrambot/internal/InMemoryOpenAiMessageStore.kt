package com.astrog.telegrambot.internal

import com.astrog.openaiapi.internal.dto.chatcompletion.Message
import com.astrog.telegrambot.domain.OpenAiMessageStore
import org.springframework.stereotype.Service

@Service
class InMemoryOpenAiMessageStore : OpenAiMessageStore {

    private val messages = mutableMapOf<String, MutableList<Message>>()

    override fun getMessages(id: String): List<Message> {
        return messages.getOrDefault(id, emptyList())
    }

    override fun addMessage(id: String, message: Message) {
        messages.merge(id, mutableListOf(message)) { old, new -> old.apply { old += new } }
    }

    override fun clearMessages(id: String) {
        messages.remove(id)
    }
}