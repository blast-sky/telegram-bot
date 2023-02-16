package com.astrog.domain.api

import com.astrog.domain.model.messaging.Message
import com.astrog.domain.model.messaging.Update

interface TelegramService {

    fun getUpdates(offset: Long): List<Update>

    fun sendMessage(chatId: Long, text: String): Message
}