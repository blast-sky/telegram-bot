package com.astrog.telegramcommon.internal.runner.idholder

interface LastProcessedIdHolder {

    val lastProcessedId: Long

    fun updateLastProcessedId(id: Long)
}