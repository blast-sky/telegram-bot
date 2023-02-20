package com.astrog.telegrambot.internal.client.dto

data class GetUpdatesRequestDto(
    val offset: Long,
    val timeout: Int,
)
