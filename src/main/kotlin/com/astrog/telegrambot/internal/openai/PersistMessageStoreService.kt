package com.astrog.telegrambot.internal.openai

import com.astrog.openaiapi.internal.dto.chatcompletion.ChatMessage
import com.astrog.telegrambot.domain.openai.OpenAiMessageStore
import com.astrog.telegrambot.internal.openai.entity.ChatMessageEntity
import com.astrog.openaiapi.internal.dto.chatcompletion.ChatRole
import com.astrog.telegrambot.internal.openai.mapper.toChatMessage
import org.springframework.stereotype.Service

@Service
class PersistMessageStoreService(
    private val messageRepository: MessageRepository,
) : OpenAiMessageStore {

    override fun getMessages(id: Long): List<ChatMessage> =
        messageRepository.findAll()
            .map(ChatMessageEntity::toChatMessage)

    override fun addMessage(id: Long, chatMessage: ChatMessage) =
        messageRepository.save(
            ChatMessageEntity(
                ownerId = id,
                role = ChatRole(chatMessage.role),
                message = chatMessage.content,
            )
        )

    override fun clearMessages(id: Long) {
        TODO("Not yet implemented")
    }
}