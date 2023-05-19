package com.astrog.telegramcommon.domain.filter.message

import com.astrog.telegramcommon.domain.model.command.extractCommand
import com.astrog.telegramcommon.domain.model.update.Message

data class CommandFilter(
    val handledCommand: String,
) : MessageFilter {

    override fun isSatisfy(message: Message): Boolean {
        return ContainsCommandFilter.isSatisfy(message) &&
            extractCommand(message.text!!).command == handledCommand
    }
}