package com.astrog.telegrambot.internal.mapping

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class TelegramCommandMapping(
    val command: String = "",
)
