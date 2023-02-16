package com.astrog.internal.client

import com.astrog.domain.api.TelegramService
import com.astrog.domain.model.messaging.Message
import com.astrog.domain.model.messaging.Update
import com.astrog.internal.client.dto.*
import com.astrog.internal.property.TelegramBotProperty
import mu.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

private val logger = KotlinLogging.logger { }

@Service
class TelegramClient(
    private val telegramRestTemplate: RestTemplate,
    private val telegramBotProperty: TelegramBotProperty,
) : TelegramService {

    override fun getUpdates(offset: Long): List<Update> = postMethod(
        method = "getUpdates",
        requestDto = GetUpdatesRequestDto(
            offset = offset,
            timeout = telegramBotProperty.longPollingTimeout,
        ),
        responseDtoClass = GetUpdatesResponseDto::class.java,
    )

    override fun sendMessage(chatId: Long, text: String): Message = postMethod(
        method = "sendMessage",
        requestDto = SendMessageRequestDto(
            chatId = chatId,
            text = text,
        ),
        responseDtoClass = SendMessageResponseDto::class.java
    )

    private inline fun <O, reified D : ResponseDto<O>> postMethod(
        method: String,
        requestDto: Any,
        responseDtoClass: Class<D>,
    ): O {
        logger.info { "Request for <$method> method." }
        val response = telegramRestTemplate.postForObject(
            method,
            requestDto,
            responseDtoClass,
        )
        return response?.result
            ?: throw RuntimeException("Unavailable results for <$method>.")
    }

}