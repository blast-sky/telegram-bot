package com.astrog.telegrambot.internal.openai

import com.astrog.telegrambot.internal.openai.entity.ChatMessageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MessageRepository : JpaRepository<ChatMessageEntity, Long>