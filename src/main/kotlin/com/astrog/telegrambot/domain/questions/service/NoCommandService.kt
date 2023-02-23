package com.astrog.telegrambot.domain.questions.service

import com.astrog.telegrambot.domain.TelegramAnnouncer
import org.springframework.stereotype.Service

@Service
class NoCommandService(
    private val telegramAnnouncer: TelegramAnnouncer,
) {

    fun process(chatId: Long) {
        telegramAnnouncer.printToUseHelp(chatId)
    }
}