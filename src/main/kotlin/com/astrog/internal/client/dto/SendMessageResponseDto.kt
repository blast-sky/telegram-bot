package com.astrog.internal.client.dto

import com.astrog.domain.model.messaging.Message

data class SendMessageResponseDto(
    override val ok: Boolean,
    override val result: Message?
): ResponseDto<Message>
