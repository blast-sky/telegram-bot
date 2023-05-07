package com.astrog.telegramcommon.domain.model.command

private val commandRegex = "^/(\\w+).*".toRegex()

private fun getCommand(line: String): String {
    return commandRegex
        .matchEntire(line)
        ?.groups
        ?.get(1)
        ?.value
        ?: ""
}

fun isContainsCommand(line: String): Boolean {
    val command = getCommand(line)
    return command.isNotBlank()
}

fun extractCommand(line: String): Command {
    val command = getCommand(line)
    return Command(
        command,
        line
            .substringAfter(command)
            .removePrefix(" ")
    )
}
