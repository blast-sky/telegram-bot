package com.astrog.telegramcommon.internal.client

import com.astrog.telegramcommon.api.TelegramService
import com.astrog.telegramcommon.api.exception.TelegramHttpException
import com.astrog.telegramcommon.domain.model.File
import com.astrog.telegramcommon.domain.model.MessageParseMode
import com.astrog.telegramcommon.domain.model.update.Message
import com.astrog.telegramcommon.domain.model.update.RawUpdate
import com.astrog.telegramcommon.internal.client.configuration.TelegramApiService
import com.astrog.telegramcommon.internal.client.configuration.TelegramFileApiService
import com.astrog.telegramcommon.internal.property.TelegramBotProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.stereotype.Service
import retrofit2.Response


@Service
class TelegramClient(
    private val telegramApiService: TelegramApiService,
    private val telegramFileApiService: TelegramFileApiService,
    private val telegramBotProperty: TelegramBotProperty,
) : TelegramService {

    private inline fun <reified T> Response<TelegramResponse<T>>.getResultOrThrow(): T {
        if (isSuccessful) {
            val responseDto = body() ?: throw IllegalStateException()
            return responseDto.result
        } else {
            val jacksonSerializer = jacksonObjectMapper()
            errorBody()?.string()?.let { error ->
                val errorResponseDto = jacksonSerializer.readValue(error, ResponseErrorDto::class.java)
                throw TelegramHttpException(errorResponseDto.errorCode, errorResponseDto.description)
            }
            throw TelegramHttpException(code())
        }
    }

    override suspend fun getUpdates(offset: Long): List<RawUpdate> = telegramApiService
        .getUpdates(offset = offset, timeout = telegramBotProperty.longPollingTimeout)
        .getResultOrThrow()

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
            .getResultOrThrow()

    override suspend fun sendImage(chatId: Long, url: String): Message = telegramApiService
        .sendPhoto(chatId = chatId, url = url)
        .getResultOrThrow()

    private suspend fun getFile(fileId: String): File = telegramApiService
        .getFile(fileId = fileId)
        .getResultOrThrow()

    override suspend fun downloadFile(fileId: String): ByteArray {
        val file = getFile(fileId)

        val responseBody = telegramFileApiService.downloadFile(
            filePath = file.filePath ?: error("filepath is null: $file")
        )

        return responseBody.bytes()
    }
}