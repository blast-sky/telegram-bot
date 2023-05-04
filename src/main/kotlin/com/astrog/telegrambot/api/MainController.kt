package com.astrog.telegrambot.api

import com.astrog.telegrambot.domain.TelegramAnnouncer
import com.astrog.telegramcommon.api.annotation.TelegramController
import com.astrog.telegramcommon.api.annotation.TelegramMapping
import com.astrog.telegramcommon.api.dsl.command.telegramCommandOf
import com.astrog.telegramcommon.api.dsl.command.telegramUnsupportedCommand

@TelegramController
class MainController(
    private val announcer: TelegramAnnouncer,
) {

    @TelegramMapping
    fun helpCommandMapping() = telegramCommandOf(
        command = "help",
        description = "Show all available commands",
    ) { message, _ -> announcer.printHelp(message.chat.id) }

    @TelegramMapping
    fun startCommandMapping() = telegramCommandOf(
        command = "start",
        description = "Show all available commands",
    ) { message, _ -> announcer.printHelp(message.chat.id) }

    @TelegramMapping
    fun unsupportedCommandMapping() = telegramUnsupportedCommand { message, _ ->
        announcer.printHelp(message.chat.id)
    }
}