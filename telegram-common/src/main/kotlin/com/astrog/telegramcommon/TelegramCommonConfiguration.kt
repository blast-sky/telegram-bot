package com.astrog.telegramcommon

import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan

@ComponentScan
class TelegramCommonConfiguration {

//    @Bean
//    fun telegramService(): TelegramService {
//        return object : TelegramService{
//            override fun getUpdates(offset: Long): List<Update> {
//                TODO("Not yet implemented")
//            }
//
//            override fun sendMessage(chatId: Long, text: String): Message {
//                TODO("Not yet implemented")
//            }
//
//        }
//    }
}