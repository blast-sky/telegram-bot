package com.astrog.telegramcommon.api

import com.astrog.telegramcommon.domain.model.ChatAction
import com.astrog.telegramcommon.domain.model.MessageParseMode
import com.astrog.telegramcommon.domain.model.update.Message
import com.astrog.telegramcommon.domain.model.update.RawUpdate

interface TelegramService {

    suspend fun getUpdates(offset: Long): List<RawUpdate>

    suspend fun sendMessage(
        chatId: Long,
        text: String,
        replyToMessageId: Long? = null,
        parseMode: MessageParseMode? = null,
        disableNotification: Boolean? = null,
        allowSendingWithoutReply: Boolean? = null,
    ): Message

    suspend fun sendImage(chatId: Long, url: String): Message

    suspend fun downloadFile(fileId: String): ByteArray

    suspend fun sendChatAction(chatId: Long, action: ChatAction): Boolean
}