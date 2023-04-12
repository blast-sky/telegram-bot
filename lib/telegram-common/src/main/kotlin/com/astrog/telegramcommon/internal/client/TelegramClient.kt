package com.astrog.telegramcommon.internal.client

import com.astrog.telegramcommon.api.TelegramService
import com.astrog.telegramcommon.domain.model.Update
import com.astrog.telegramcommon.domain.model.UpdateContent.Message
import com.astrog.telegramcommon.internal.client.dto.GetUpdatesRequest
import com.astrog.telegramcommon.internal.client.dto.GetUpdatesResponse
import com.astrog.telegramcommon.internal.client.dto.ResponseDto
import com.astrog.telegramcommon.internal.client.dto.SendMessageRequest
import com.astrog.telegramcommon.internal.client.dto.SendMessageResponse
import com.astrog.telegramcommon.internal.client.dto.SendPhotoRequest
import com.astrog.telegramcommon.internal.property.TelegramBotProperty
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class TelegramClient(
    private val telegramRestTemplate: RestTemplate,
    private val telegramBotProperty: TelegramBotProperty,
) : TelegramService {

    override fun getUpdates(offset: Long): List<Update> = postMethod(
        method = "getUpdates",
        requestDto = GetUpdatesRequest(
            offset = offset,
            timeout = telegramBotProperty.longPollingTimeout,
        ),
        responseDtoClass = GetUpdatesResponse::class.java,
    )

    override fun sendMessage(chatId: Long, text: String): Message = postMethod(
        method = "sendMessage",
        requestDto = SendMessageRequest(
            chatId = chatId,
            text = text,
        ),
        responseDtoClass = SendMessageResponse::class.java,
    )

    override fun sendImage(chatId: Long, url: String) = postMethod(
        method = "sendPhoto",
        requestDto = SendPhotoRequest(
            chaId = chatId,
            photo = url,
        ),
        responseDtoClass = SendMessageResponse::class.java,
    )

    private inline fun <O, reified D : ResponseDto<O>> postMethod(
        method: String,
        requestDto: Any,
        responseDtoClass: Class<D>,
    ): O {
        val response = telegramRestTemplate.postForObject(
            method,
            requestDto,
            responseDtoClass,
        )
        return response?.result ?: throw RuntimeException("Unavailable results for <$method>.")
    }

}