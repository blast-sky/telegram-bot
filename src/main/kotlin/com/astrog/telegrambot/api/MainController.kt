package com.astrog.telegrambot.api

import com.astrog.telegrambot.domain.telegram.TelegramConfiguration
import com.astrog.telegrambot.domain.telegram.TelegramMapping
import com.astrog.telegrambot.internal.bot.Dispatcher
import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.ChatId

@TelegramConfiguration
class MainController{

    @TelegramMapping
    fun helpMapping(): Dispatcher = {
        fun Bot.sendHelpToChat(chatId: Long) {
            sendMessage(ChatId.fromId(chatId), HELP)
        }

        command("help") {
            bot.sendHelpToChat(message.chat.id)
        }

        command("start") {
            bot.sendHelpToChat(message.chat.id)
        }
    }

    private companion object {
        const val HELP = "ƒоступные команды дл€ приватного чата: " // TODO
    }
}