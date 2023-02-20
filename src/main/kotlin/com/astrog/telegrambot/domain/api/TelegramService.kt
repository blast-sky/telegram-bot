package com.astrog.telegrambot.domain.api

import com.astrog.telegrambot.domain.model.messaging.Message
import com.astrog.telegrambot.domain.model.messaging.Update

interface TelegramService {

    fun getUpdates(offset: Long): List<Update>

    fun sendMessage(chatId: Long, text: String): Message
}