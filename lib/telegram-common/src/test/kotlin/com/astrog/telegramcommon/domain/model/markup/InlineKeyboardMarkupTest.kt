package com.astrog.telegramcommon.domain.model.markup

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class InlineKeyboardMarkupTest {

    @Test
    fun serialization() {
        val mapper = jacksonObjectMapper()
        val obj  = InlineKeyboardMarkup(listOf(
            listOf(InlineKeyboardButton("123", "456"))
        ))
        val asString = mapper.writeValueAsString(obj)
        assertEquals("{\"inline_keyboard\":[[{\"text\":\"123\",\"callback_data\":\"456\"}]]}", asString)
    }
}