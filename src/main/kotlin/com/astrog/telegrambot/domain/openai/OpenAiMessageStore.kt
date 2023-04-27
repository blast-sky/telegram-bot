package com.astrog.telegrambot.domain.openai

import com.astrog.openaiapi.internal.dto.chatcompletion.ChatMessage

interface OpenAiMessageStore {

    fun getMessages(id: Long): List<ChatMessage>

    fun addMessage(id: Long, chatMessage: ChatMessage)

    fun clearMessages(id: Long)
}