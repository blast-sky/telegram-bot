package com.astrog.telegramcommon.domain.filter.message

import com.astrog.telegramcommon.domain.model.update.Message

object TextFilter : MessageFilter {

    override fun isSatisfy(message: Message): Boolean {
        return message.text != null
    }
}