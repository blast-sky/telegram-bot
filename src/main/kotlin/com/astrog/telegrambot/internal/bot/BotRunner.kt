package com.astrog.telegrambot.internal.bot

import com.github.kotlintelegrambot.Bot
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class BotRunner(
    private val bot: Bot,
) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        bot.startPolling()
    }
}