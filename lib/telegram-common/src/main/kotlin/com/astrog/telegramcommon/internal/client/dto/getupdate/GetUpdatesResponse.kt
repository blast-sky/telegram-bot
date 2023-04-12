package com.astrog.telegramcommon.internal.client.dto.getupdate

import com.astrog.telegramcommon.domain.model.Update
import com.astrog.telegramcommon.internal.client.dto.ResponseDto

data class GetUpdatesResponse(
    override val ok: Boolean,
    override val result: List<Update>?,
) : ResponseDto<List<Update>>
