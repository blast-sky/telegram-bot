package com.astrog.telegramcommon.internal.client.dto

import com.astrog.telegramcommon.domain.model.Message

data class SendMessageResponseDto(
    override val ok: Boolean,
    override val result: Message?
) : ResponseDto<Message>
