package com.astrog.domain

import com.astrog.domain.model.Message
import com.astrog.domain.model.Update

interface TelegramService {

    fun getUpdates(offset: Long): List<Update>

    fun sendMessage(chatId: Long, text: String): Message
}