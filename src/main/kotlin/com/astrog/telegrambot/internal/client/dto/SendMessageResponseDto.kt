package com.astrog.telegrambot.internal.client.dto

import com.astrog.telegrambot.domain.model.messaging.Message

data class SendMessageResponseDto(
    override val ok: Boolean,
    override val result: Message?
): ResponseDto<Message>
