package com.astrog.telegramcommon.api

import java.lang.annotation.Inherited

@Repeatable
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.ANNOTATION_CLASS)
annotation class TelegramCommandMapping(
    val command: String = "",
    val description: String = "",
)
