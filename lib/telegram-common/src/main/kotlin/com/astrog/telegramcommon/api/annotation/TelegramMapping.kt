package com.astrog.telegramcommon.api.annotation

import org.springframework.context.annotation.Bean

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
@Bean
annotation class TelegramMapping
