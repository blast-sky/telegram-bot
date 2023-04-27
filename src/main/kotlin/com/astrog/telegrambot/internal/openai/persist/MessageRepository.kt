package com.astrog.telegrambot.internal.openai.persist

import com.astrog.telegrambot.internal.openai.persist.entity.ChatMessageEntity
import com.astrog.telegrambot.persistStore
import org.springframework.context.annotation.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Profile(persistStore)
@Repository
interface MessageRepository : JpaRepository<ChatMessageEntity, Long> {

    fun deleteByOwnerId(ownerId: Long)

    fun findAllByOwnerId(ownerId: Long): List<ChatMessageEntity>
}