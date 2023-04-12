package com.astrog.telegramcommon.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

data class Update(
    @JsonProperty("update_id")
    val updateId: Long,
    val message: UpdateContent.Message?,
    @JsonProperty("edited_message")
    val editedMessage: UpdateContent.EditedMessage?,
    @JsonProperty("channel_post")
    val channelPost: UpdateContent.ChannelPost?,
    @JsonProperty("edited_channel_post")
    val editedChannelPost: UpdateContent.EditedChannelPost?,
) {

    val firstNotNullUpdateContent = listOfNotNull(
        message,
        editedMessage,
        channelPost,
        editedChannelPost,
    ).first()
}
