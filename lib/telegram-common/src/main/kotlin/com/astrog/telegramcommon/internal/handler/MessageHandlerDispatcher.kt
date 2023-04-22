package com.astrog.telegramcommon.internal.handler

import com.astrog.telegramcommon.domain.model.UpdateContent

typealias MessageHandlerDispatcher = suspend (message: UpdateContent.Message) -> Unit
