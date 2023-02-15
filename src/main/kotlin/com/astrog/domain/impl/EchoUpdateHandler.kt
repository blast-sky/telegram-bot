package com.astrog.domain.impl

import com.astrog.domain.api.UpdateHandler
import com.astrog.domain.model.Update
import com.astrog.internal.TelegramClient
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Service

@Order(0)
@Service
class EchoUpdateHandler(
    private val client: TelegramClient,
) : UpdateHandler {

    override fun handle(update: Update): Boolean {
        update.message?.apply {
            if (text != null) {
                client.sendMessage(chat.id, text)
            }
        }
        return true
    }
}