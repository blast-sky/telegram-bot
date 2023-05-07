package com.astrog.telegramcommon.api

import com.astrog.telegramcommon.domain.model.update.Message
import com.astrog.telegramcommon.domain.model.update.RawUpdate
import com.astrog.telegramcommon.domain.model.update.Update

interface TelegramService {

    suspend fun getUpdates(offset: Long): List<RawUpdate>

    suspend fun sendMessage(chatId: Long, text: String, replyToMessageId: Long? = null): Message

    suspend fun sendImage(chatId: Long, url: String): Message

    suspend fun downloadFile(fileId: String): ByteArray
}