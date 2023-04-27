package com.astrog.telegrambot.internal.openai.persist

import com.astrog.openaiapi.internal.dto.chatcompletion.ChatMessage
import com.astrog.telegrambot.domain.openai.OpenAiMessageStore
import com.astrog.telegrambot.internal.openai.persist.entity.ChatMessageEntity
import com.astrog.telegrambot.internal.openai.persist.mapper.toChatMessage
import com.astrog.telegrambot.persistStore
import jakarta.transaction.Transactional
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Profile(persistStore)
@Transactional
@Service
class PersistOpenAiMessageStoreService(
    private val messageRepository: MessageRepository,
) : OpenAiMessageStore {

    override fun getMessages(id: Long): List<ChatMessage> =
        messageRepository.findAllByOwnerId(id)
            .map(ChatMessageEntity::toChatMessage)

    override fun addMessage(id: Long, chatMessage: ChatMessage): Unit =
        messageRepository.save(
            ChatMessageEntity(
                ownerId = id,
                role = chatMessage.role,
                message = chatMessage.content,
            )
        ).run { }

    override fun clearMessages(id: Long): Unit =
        messageRepository.deleteByOwnerId(id)
}