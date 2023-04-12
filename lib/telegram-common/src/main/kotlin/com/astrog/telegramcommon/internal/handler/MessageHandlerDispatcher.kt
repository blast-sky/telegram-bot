package com.astrog.telegramcommon.internal.handler

import com.astrog.telegramcommon.domain.model.UpdateContent

typealias MessageHandlerDispatcher = (message: UpdateContent.BaseMessage) -> Unit