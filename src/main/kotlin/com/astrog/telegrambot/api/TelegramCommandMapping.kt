package com.astrog.telegrambot.api

import com.astrog.telegrambot.domain.model.messaging.Message
import com.astrog.telegrambot.domain.service.AnswerService
import com.astrog.telegrambot.domain.service.NoCommandService
import com.astrog.telegrambot.domain.service.StartService
import com.astrog.telegrambot.internal.mapping.TelegramCommandMapping
import org.springframework.stereotype.Component

@Component
class TelegramCommandMapping(
    private val startService: StartService,
    private val answerService: AnswerService,
    private val noCommandService: NoCommandService,
) {

    @TelegramCommandMapping("start")
    fun onStartCommand(message: Message, args: String) {
        startService.process(chatId = message.chat.id)
    }

    @TelegramCommandMapping("answer")
    fun onAnswerCommand(message: Message, args: String) {
        answerService.process(chatId = message.chat.id, args = args)
    }

    @TelegramCommandMapping
    fun onNoCommand(message: Message, args: String) {
        noCommandService.process(chatId = message.chat.id)
    }
}