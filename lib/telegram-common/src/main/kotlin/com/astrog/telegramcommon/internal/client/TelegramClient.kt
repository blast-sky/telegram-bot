package com.astrog.telegramcommon.internal.client

import com.astrog.telegramcommon.api.TelegramService
import com.astrog.telegramcommon.domain.model.File
import com.astrog.telegramcommon.domain.model.Update
import com.astrog.telegramcommon.domain.model.UpdateContent.Message
import com.astrog.telegramcommon.internal.client.configuration.TelegramApiService
import com.astrog.telegramcommon.internal.client.configuration.TelegramFileApiService
import com.astrog.telegramcommon.internal.property.TelegramBotProperty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import java.io.FileOutputStream


@Service
class TelegramClient(
    private val telegramApiService: TelegramApiService,
    private val telegramFileApiService: TelegramFileApiService,
    private val telegramBotProperty: TelegramBotProperty,
) : TelegramService {

    override suspend fun getUpdates(offset: Long): List<Update> = telegramApiService
        .getUpdates(offset = offset, timeout = telegramBotProperty.longPollingTimeout)
        .result

    override suspend fun sendMessage(chatId: Long, text: String): Message = telegramApiService
        .sendMessage(chatId = chatId, text = text)
        .result

    override suspend fun sendImage(chatId: Long, url: String): Message = telegramApiService
        .sendImage(chatId = chatId, url = url)
        .result

    private fun getFile(fileId: String): File = telegramApiService
        .getFile(fileId = fileId)
        .result

    override suspend fun downloadFile(fileId: String): String {
        val file = getFile(fileId)

        val bytes = telegramFileApiService.downloadFile(
            filePath = file.filePath
                ?: error("filepath is null: $file")
        ) ?: error("null bytes")

        val fileName = "voice-${file.fileUniqueId}.ogg"

        withContext(Dispatchers.IO) {
            FileOutputStream(fileName).use { out -> out.write(bytes) }
        }

        return fileName
    }
}