package com.astrog.telegramcommon.internal.client.dto.getfile

import com.astrog.telegramcommon.domain.model.File
import com.astrog.telegramcommon.internal.client.dto.ResponseDto

data class GetFileResponse(
    override val ok: Boolean,
    override val result: File?,
) : ResponseDto<File>