package com.astrog.telegrambot.domain.telegram

import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.extensions.filters.Filter

object VoiceFilter : Filter {

    override fun Message.predicate(): Boolean {
        return voice != null
    }
}