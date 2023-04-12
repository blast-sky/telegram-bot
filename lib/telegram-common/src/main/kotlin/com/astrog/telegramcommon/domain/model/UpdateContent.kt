package com.astrog.telegramcommon.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

sealed class UpdateContent {

    abstract class BaseMessage(
        @JsonProperty("message_id")
        open val messageId: Long,
        open val chat: Chat,
        open val from: User?,
        open val text: String?,
    ) : UpdateContent()

    data class Message(
        override val messageId: Long,
        override val chat: Chat,
        override val from: User?,
        override val text: String?,
    ) : BaseMessage(messageId, chat, from, text)

    data class EditedMessage(
        override val messageId: Long,
        override val chat: Chat,
        override val from: User?,
        override val text: String?,
    ) : BaseMessage(messageId, chat, from, text)

    data class ChannelPost(
        override val messageId: Long,
        override val chat: Chat,
        override val from: User?,
        override val text: String?,
    ) : BaseMessage(messageId, chat, from, text)

    data class EditedChannelPost(
        override val messageId: Long,
        override val chat: Chat,
        override val from: User?,
        override val text: String?,
    ) : BaseMessage(messageId, chat, from, text)
}