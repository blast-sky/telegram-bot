package com.astrog.telegramcommon.api

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class TelegramCommandMapping(
    val command: String = "",
)
