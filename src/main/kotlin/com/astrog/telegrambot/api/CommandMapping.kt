package com.astrog.telegrambot.api

import com.astrog.telegrambot.domain.questions.service.AnswerService
import com.astrog.telegrambot.domain.questions.service.NoCommandService
import com.astrog.telegrambot.domain.questions.service.StartService
import com.astrog.telegramcommon.api.TelegramCommandMapping
import com.astrog.telegramcommon.domain.model.Message
import org.springframework.stereotype.Component

@Component
class CommandMapping(
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

    @TelegramCommandMapping("completion")
    fun onCompletion(message: Message, args: String) {

    }

    @TelegramCommandMapping
    fun onNoCommand(message: Message, args: String) {
        noCommandService.process(chatId = message.chat.id)
    }
}