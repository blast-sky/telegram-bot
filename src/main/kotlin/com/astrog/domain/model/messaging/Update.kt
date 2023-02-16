package com.astrog.domain.model.messaging

import com.fasterxml.jackson.annotation.JsonProperty

data class Update(
    @JsonProperty("update_id")
    val updateId: Long,
    val message: Message?,
)
