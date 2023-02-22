package com.astrog.telegramcommon.internal.client.dto

data class GetUpdatesRequest(
    val offset: Long,
    val timeout: Int,
)
