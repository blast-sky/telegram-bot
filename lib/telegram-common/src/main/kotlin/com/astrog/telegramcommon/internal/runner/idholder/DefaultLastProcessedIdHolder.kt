package com.astrog.telegramcommon.internal.runner.idholder

import org.springframework.stereotype.Component

@Component
class DefaultLastProcessedIdHolder : LastProcessedIdHolder {

    private var id = Long.MAX_VALUE

    override val lastProcessedId: Long
        get() = id

    override fun updateLastProcessedId(id: Long) {
        this.id = id
    }
}