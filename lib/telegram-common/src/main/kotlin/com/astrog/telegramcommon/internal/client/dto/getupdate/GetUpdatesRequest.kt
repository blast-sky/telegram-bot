package com.astrog.telegramcommon.internal.client.dto.getupdate

data class GetUpdatesRequest(
    val offset: Long,
    val timeout: Int,
)
