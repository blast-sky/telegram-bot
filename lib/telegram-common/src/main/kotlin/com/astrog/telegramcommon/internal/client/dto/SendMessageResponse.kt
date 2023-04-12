package com.astrog.telegramcommon.internal.client.dto

import com.astrog.telegramcommon.domain.model.UpdateContent

data class SendMessageResponse(
    override val ok: Boolean,
    override val result: UpdateContent.Message?
) : ResponseDto<UpdateContent.Message>
