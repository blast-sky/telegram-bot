package com.astrog.telegramcommon.internal.mapping

import com.astrog.telegramcommon.domain.model.Message

typealias MethodInvocation = (message: Message, args: String) -> Unit

data class Command(
    val command: String,
    val description: String,
    val method: MethodInvocation,
)