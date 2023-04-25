package com.astrog.telegramcommon.domain.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Update(
    @JsonProperty("update_id")
    val updateId: Long,
    @JsonProperty("message")
    private val message: UpdateContent.Message?,
    @JsonProperty("edited_message")
    private val editedMessage: UpdateContent.Message?,
    @JsonProperty("channel_post")
    private val channelPost: UpdateContent.Message?,
    @JsonProperty("edited_channel_post")
    private val editedChannelPost: UpdateContent.Message?,
) {

    // Take first not null message from update and set message type
    val messageWithType = mapOf(
        MessageType.MESSAGE to message,
        MessageType.EDITED_MESSAGE to editedMessage,
        MessageType.CHANNEL_POST to channelPost,
        MessageType.EDITED_CHANNEL_POST to editedChannelPost,
    )
        .entries
        .firstOrNull { (_, value) -> value != null }
        ?.run { value!!.apply { type = key } }
}
