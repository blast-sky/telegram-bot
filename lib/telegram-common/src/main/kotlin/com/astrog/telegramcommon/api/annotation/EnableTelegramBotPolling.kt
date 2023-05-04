package com.astrog.telegramcommon.api.annotation

import com.astrog.telegramcommon.TelegramCommonConfiguration
import org.springframework.context.annotation.Import

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Import(TelegramCommonConfiguration::class)
annotation class EnableTelegramBotPolling
