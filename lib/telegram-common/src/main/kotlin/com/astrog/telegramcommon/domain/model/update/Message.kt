package com.astrog.telegramcommon.domain.model.update

import com.astrog.telegramcommon.domain.model.chat.Chat
import com.astrog.telegramcommon.domain.model.User
import com.astrog.telegramcommon.domain.model.Voice
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class Message(
    @JsonProperty("message_id")
    val messageId: Long,
    @JsonProperty("chat")
    val chat: Chat,
    @JsonProperty("from")
    val from: User?,
    @JsonProperty("text")
    val text: String?,
    @JsonProperty("voice")
    val voice: Voice?,
    @JsonProperty("reply_to_message")
    val replyToMessage: Message?,
) : Update() {
    lateinit var type: MessageType
}