package com.astrog.internal.dto

import com.astrog.domain.model.Update

data class GetUpdatesResponseDto(
    override val ok: Boolean,
    override val result: List<Update>?,
) : ResponseDto<List<Update>>
