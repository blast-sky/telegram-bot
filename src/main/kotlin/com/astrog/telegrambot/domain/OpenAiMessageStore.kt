package com.astrog.telegrambot.domain

import com.astrog.openaiapi.internal.dto.chatcompletion.Message

interface OpenAiMessageStore {

    fun getMessages(id: String): List<Message>

    fun addMessage(id: String, message: Message)

    fun clearMessages(id: String)
}