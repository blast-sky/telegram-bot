package com.astrog.telegrambot.internal.openai.mapper

import com.astrog.openaiapi.internal.dto.chatcompletion.ChatMessage
import com.astrog.telegrambot.internal.openai.entity.ChatMessageEntity

fun ChatMessageEntity.toChatMessage() = ChatMessage(
    role = role.type,
    content = message,
)