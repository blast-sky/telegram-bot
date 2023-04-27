package com.astrog.telegrambot.internal.openai.persist.entity

import com.astrog.openaiapi.internal.dto.chatcompletion.ChatRole
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.Table

@Entity
@Table(name = "message")
data class ChatMessageEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long? = null,

    @Column(name = "owner_id")
    val ownerId: Long,

    val role: ChatRole,

    @Lob
    val message: String,
)
