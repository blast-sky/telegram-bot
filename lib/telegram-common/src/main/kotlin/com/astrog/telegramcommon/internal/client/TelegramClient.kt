package com.astrog.telegramcommon.internal.client

import com.astrog.telegramcommon.api.TelegramService
import com.astrog.telegramcommon.domain.model.File
import com.astrog.telegramcommon.domain.model.MessageParseMode
import com.astrog.telegramcommon.domain.model.update.Message
import com.astrog.telegramcommon.domain.model.update.RawUpdate
import com.astrog.telegramcommon.internal.client.configuration.TelegramApiService
import com.astrog.telegramcommon.internal.client.configuration.TelegramFileApiService
import com.astrog.telegramcommon.internal.property.TelegramBotProperty
import org.springframework.stereotype.Service


@Service
class TelegramClient(
    private val telegramApiService: TelegramApiService,
    private val telegramFileApiService: TelegramFileApiService,
    private val telegramBotProperty: TelegramBotProperty,
) : TelegramService {

    override suspend fun getUpdates(offset: Long): List<RawUpdate> = telegramApiService
        .getUpdates(offset = offset, timeout = telegramBotProperty.longPollingTimeout)
        .result

    override suspend fun sendMessage(
        chatId: Long,
        text: String,
        replyToMessageId: Long?,
        parseMode: MessageParseMode?,
        disableNotification: Boolean?,
        allowSendingWithoutReply: Boolean?
    ): Message =
        telegramApiService
            .sendMessage(
                chatId = chatId,
                text = text,
                replyToMessageId = replyToMessageId,
                parseMode = parseMode,
                disableNotification = disableNotification,
                allowSendingWithoutReply = allowSendingWithoutReply,
            )
            .result

    override suspend fun sendImage(chatId: Long, url: String): Message = telegramApiService
        .sendPhoto(chatId = chatId, url = url)
        .result

    private suspend fun getFile(fileId: String): File = telegramApiService
        .getFile(fileId = fileId)
        .result

    override suspend fun downloadFile(fileId: String): ByteArray {
        val file = getFile(fileId)

        val responseBody = telegramFileApiService.downloadFile(
            filePath = file.filePath ?: error("filepath is null: $file")
        )

        return responseBody.bytes()
    }
}