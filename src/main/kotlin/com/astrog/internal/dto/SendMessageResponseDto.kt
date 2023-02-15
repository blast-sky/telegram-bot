package com.astrog.internal.dto

import com.astrog.domain.model.Message

data class SendMessageResponseDto(
    override val ok: Boolean,
    override val result: Message?
): ResponseDto<Message>
