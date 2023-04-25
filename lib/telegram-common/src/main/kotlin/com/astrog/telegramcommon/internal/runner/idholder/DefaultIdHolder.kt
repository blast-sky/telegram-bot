package com.astrog.telegramcommon.internal.runner.idholder

import org.springframework.stereotype.Component

@Component
class DefaultIdHolder : IdHolder {

    private var _id = Long.MAX_VALUE

    override val id: Long
        get() = _id

    override fun update(id: Long) {
        this._id = id
    }
}