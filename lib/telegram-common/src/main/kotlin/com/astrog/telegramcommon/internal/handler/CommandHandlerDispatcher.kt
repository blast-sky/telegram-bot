package com.astrog.telegramcommon.internal.handler

import com.astrog.telegramcommon.domain.model.UpdateContent

typealias CommandDispatcher = suspend (message: UpdateContent.Message, args: String) -> Unit