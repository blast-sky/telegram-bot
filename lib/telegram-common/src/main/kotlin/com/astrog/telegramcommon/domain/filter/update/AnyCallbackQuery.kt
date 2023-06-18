package com.astrog.telegramcommon.domain.filter.update

import com.astrog.telegramcommon.domain.filter.UpdateFilter
import com.astrog.telegramcommon.domain.model.update.CallbackQuery
import com.astrog.telegramcommon.domain.model.update.Update

object AnyCallbackQuery : UpdateFilter {

    override fun isSatisfy(update: Update): Boolean {
        return update is CallbackQuery
    }
}