package com.astrog.api

import com.astrog.domain.model.messaging.Message
import com.astrog.domain.service.AnswerService
import com.astrog.domain.service.NoCommandService
import com.astrog.domain.service.StartService
import com.astrog.internal.mapping.TelegramCommandMapping
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