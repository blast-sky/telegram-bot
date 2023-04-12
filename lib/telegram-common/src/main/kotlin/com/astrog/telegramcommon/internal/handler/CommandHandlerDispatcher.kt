package com.astrog.telegramcommon.internal.handler

import com.astrog.telegramcommon.domain.model.UpdateContent

typealias CommandDispatcher = (message: UpdateContent.Message, args: String) -> Unit