package com.astrog.telegramcommon.domain.filter.message

import com.astrog.telegramcommon.domain.model.chat.ChatType
import com.astrog.telegramcommon.domain.model.update.Message

object IsPrivateChatFilter : MessageFilter {

    override fun isSatisfy(message: Message): Boolean {
        return message.chat.type == ChatType.PRIVATE
    }
}