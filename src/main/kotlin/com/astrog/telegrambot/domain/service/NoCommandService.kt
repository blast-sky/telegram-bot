package com.astrog.telegrambot.domain.service

import org.springframework.stereotype.Service

@Service
class NoCommandService(
    private val telegramAnnouncer: TelegramAnnouncer,
) {

    fun process(chatId: Long) {
        telegramAnnouncer.printIDoNotUnderstand(chatId)
    }
}