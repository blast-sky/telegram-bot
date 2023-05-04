package com.astrog.telegramcommon.api

import com.astrog.telegramcommon.domain.model.Update
import com.astrog.telegramcommon.domain.model.UpdateContent

interface TelegramService {

    suspend fun getUpdates(offset: Long): List<Update>

    suspend fun sendMessage(chatId: Long, text: String, replyToMessageId: Long? = null): UpdateContent.Message

    suspend fun sendImage(chatId: Long, url: String): UpdateContent.Message

    suspend fun downloadFile(fileId: String): ByteArray
}