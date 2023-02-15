package com.astrog.internal.dto

data class GetUpdatesRequestDto(
    val offset: Long,
    val timeout: Int,
)
