package com.astrog.telegramcommon.internal.client.dto

data class GetUpdatesRequestDto(
    val offset: Long,
    val timeout: Int,
)
