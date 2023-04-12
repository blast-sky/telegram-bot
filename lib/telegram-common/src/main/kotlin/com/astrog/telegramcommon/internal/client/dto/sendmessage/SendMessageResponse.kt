package com.astrog.telegramcommon.internal.client.dto.sendmessage

import com.astrog.telegramcommon.domain.model.UpdateContent
import com.astrog.telegramcommon.internal.client.dto.ResponseDto

data class SendMessageResponse(
    override val ok: Boolean,
    override val result: UpdateContent.Message?
) : ResponseDto<UpdateContent.Message>
