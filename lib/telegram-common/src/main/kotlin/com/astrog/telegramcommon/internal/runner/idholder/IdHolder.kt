package com.astrog.telegramcommon.internal.runner.idholder

interface IdHolder {

    val id: Long

    fun update(id: Long)
}