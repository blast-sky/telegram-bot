package com.astrog.internal.dto

interface ResponseDto<T> {
    val ok: Boolean
    val result: T?
}
