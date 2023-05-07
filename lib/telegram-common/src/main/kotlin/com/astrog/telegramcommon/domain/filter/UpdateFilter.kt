package com.astrog.telegramcommon.domain.filter

import com.astrog.telegramcommon.domain.model.update.Update

interface UpdateFilter {

    fun isSatisfy(update: Update): Boolean
}