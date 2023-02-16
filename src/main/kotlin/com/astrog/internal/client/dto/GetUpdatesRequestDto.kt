package com.astrog.internal.client.dto

data class GetUpdatesRequestDto(
    val offset: Long,
    val timeout: Int,
)
