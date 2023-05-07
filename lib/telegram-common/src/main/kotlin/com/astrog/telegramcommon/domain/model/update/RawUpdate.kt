package com.astrog.telegramcommon.domain.model.update

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class RawUpdate(
    @JsonProperty("update_id")
    val updateId: Long,
    @JsonProperty("message")
    private val message: Message?,
    @JsonProperty("edited_message")
    private val editedMessage: Message?,
    @JsonProperty("channel_post")
    private val channelPost: Message?,
    @JsonProperty("edited_channel_post")
    private val editedChannelPost: Message?,
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
