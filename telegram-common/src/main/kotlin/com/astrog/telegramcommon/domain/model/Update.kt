package com.astrog.telegramcommon.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Update(
    @JsonProperty("update_id")
    val updateId: Long,
    val message: Message?,
)
