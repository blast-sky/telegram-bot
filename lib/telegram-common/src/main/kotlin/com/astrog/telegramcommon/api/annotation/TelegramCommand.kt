package com.astrog.telegramcommon.api.annotation

@Repeatable
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class TelegramCommand(
    val command: String = "",
    val description: String = "",
)
