package com.astrog.telegrambot.internal.openai.persist.mapper

import com.astrog.openaiapi.internal.dto.chatcompletion.ChatMessage
import com.astrog.telegrambot.internal.openai.persist.entity.ChatMessageEntity

fun ChatMessageEntity.toChatMessage() = ChatMessage(
    role = role,
    content = message,
)