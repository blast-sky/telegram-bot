package com.astrog.telegrambot.internal.client.dto

import com.astrog.telegrambot.domain.model.messaging.Update

data class GetUpdatesResponseDto(
    override val ok: Boolean,
    override val result: List<Update>?,
) : ResponseDto<List<Update>>
