package com.astrog.telegramcommon.domain.filter.message

import com.astrog.telegramcommon.domain.filter.UpdateFilter
import com.astrog.telegramcommon.domain.model.update.Message
import com.astrog.telegramcommon.domain.model.update.Update

interface MessageFilter : UpdateFilter {

    override fun isSatisfy(update: Update): Boolean {
        return update is Message && isSatisfy(update)
    }

    fun isSatisfy(message: Message): Boolean
}