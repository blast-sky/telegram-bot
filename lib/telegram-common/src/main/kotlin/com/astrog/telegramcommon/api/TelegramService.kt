package com.astrog.telegramcommon.api

import com.astrog.telegramcommon.domain.model.Update
import com.astrog.telegramcommon.domain.model.UpdateContent

interface TelegramService {

    fun getUpdates(offset: Long): List<Update>

    fun sendMessage(chatId: Long, text: String): UpdateContent.Message

    fun sendImage(chatId: Long, url: String): UpdateContent.Message

    fun downloadFile(fileId: String): String
}