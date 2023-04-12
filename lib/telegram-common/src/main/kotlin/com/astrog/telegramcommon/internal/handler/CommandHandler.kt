package com.astrog.telegramcommon.internal.handler


data class CommandHandler(
    val command: String,
    val description: String,
    val dispatcher: CommandDispatcher,
)