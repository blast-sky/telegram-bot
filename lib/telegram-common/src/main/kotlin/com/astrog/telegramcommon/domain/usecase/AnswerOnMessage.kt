package com.astrog.telegramcommon.domain.usecase

import com.astrog.telegramcommon.api.TelegramService
import com.astrog.telegramcommon.domain.model.UpdateContent


fun TelegramService.textAnswer(message: UpdateContent.BaseMessage, text: String) {
    sendMessage(message.chat.id, text)
}