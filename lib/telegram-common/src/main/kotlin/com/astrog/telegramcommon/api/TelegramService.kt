package com.astrog.telegramcommon.api

import com.astrog.telegramcommon.domain.model.Message
import com.astrog.telegramcommon.domain.model.Update

interface TelegramService {

    fun getUpdates(offset: Long): List<Update>

    fun sendMessage(chatId: Long, text: String): Message

    fun sendImage(chatId: Long, url: String): Message
}