package com.astrog.telegramcommon.domain.model.update

import com.astrog.telegramcommon.domain.model.User
import com.fasterxml.jackson.annotation.JsonProperty

data class CallbackQuery(
    val id: String,
    val from: User,
    val message: Message?,
    @JsonProperty("inline_message_id")
    val inlineMessageId: String?,
    @JsonProperty("chat_instance")
    val chatInstance: String,
    val data: String?,
    @JsonProperty("game_short_name")
    val gameShortName: String?,
) : Update()
