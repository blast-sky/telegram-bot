package com.astrog.telegramcommon.internal.client.dto

interface ResponseDto<T> {
    val ok: Boolean
    val result: T?
}
