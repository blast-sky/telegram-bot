package com.astrog.telegramcommon.internal.client

import com.astrog.telegramcommon.api.TelegramService
import com.astrog.telegramcommon.api.exception.TelegramHttpException
import com.astrog.telegramcommon.domain.model.File
import com.astrog.telegramcommon.domain.model.Update
import com.astrog.telegramcommon.domain.model.UpdateContent.Message
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

    override suspend fun getUpdates(offset: Long): List<Update> = telegramApiService
        .getUpdates(offset = offset, timeout = telegramBotProperty.longPollingTimeout)
        .checkOnError()

    private inline fun <reified T> Response<ResponseDto<T>>.checkOnError(): T {
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

    override suspend fun sendMessage(chatId: Long, text: String, replyToMessageId: Long?): Message =
        telegramApiService
            .sendMessage(chatId = chatId, text = text, replyToMessageId = replyToMessageId)
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