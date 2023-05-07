package com.astrog.telegramcommon.domain.filter.message

import com.astrog.telegramcommon.domain.model.update.Message

object AnyMessageFilter : MessageFilter {

    override fun isSatisfy(message: Message): Boolean {
        return true
    }
}