package com.astrog.telegramcommon.api.exception

class TelegramHttpException(
    val errorCode: Int,
    val description: String? = null,
) : RuntimeException("Telegram response with not successful code: <$errorCode>, description: <$description>")
