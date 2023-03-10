package com.astrog.telegramcommon.api.annotation


@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class TelegramUnsupportedCommandMapping(
    val command: String = unsupportedCommandMapping,
)

const val unsupportedCommandMapping = "TelegramUnsupportedCommandMapping$%$%"