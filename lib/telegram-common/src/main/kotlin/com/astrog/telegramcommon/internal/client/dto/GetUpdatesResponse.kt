package com.astrog.telegramcommon.internal.client.dto

import com.astrog.telegramcommon.domain.model.Update

data class GetUpdatesResponse(
    override val ok: Boolean,
    override val result: List<Update>?,
) : ResponseDto<List<Update>>
