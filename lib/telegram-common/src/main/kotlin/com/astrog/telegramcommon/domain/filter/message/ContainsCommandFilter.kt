package com.astrog.telegramcommon.domain.filter.message

import com.astrog.telegramcommon.domain.model.command.isContainsCommand
import com.astrog.telegramcommon.domain.model.update.Message

object ContainsCommandFilter : MessageFilter {

    override fun isSatisfy(message: Message): Boolean {
        return message.text != null && isContainsCommand(message.text)
    }
}