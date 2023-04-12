package com.astrog.telegramcommon.domain.usecase

import com.astrog.telegramcommon.api.TelegramService
import org.springframework.stereotype.Component

@Component
class TelegramServiceContextProducer(
    private val telegramService: TelegramService,
) {

    val messageContext = Unit
}