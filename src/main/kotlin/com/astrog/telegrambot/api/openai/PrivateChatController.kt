package com.astrog.telegrambot.api.openai

import com.astrog.telegrambot.domain.service.CallbackData
import com.astrog.telegrambot.domain.service.CompletingService
import com.astrog.telegrambot.domain.service.TranscriptionService
import com.astrog.telegrambot.domain.telegram.TelegramConfiguration
import com.astrog.telegrambot.domain.telegram.TelegramMapping
import com.astrog.telegrambot.domain.telegram.VoiceFilter
import com.astrog.telegrambot.internal.bot.Dispatcher
import com.github.kotlintelegrambot.dispatcher.callbackQuery
import com.github.kotlintelegrambot.dispatcher.message
import com.github.kotlintelegrambot.entities.ChatAction
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.extensions.filters.Filter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@TelegramConfiguration
class PrivateChatController(
    private val completingService: CompletingService,
    private val transcriptionService: TranscriptionService,
) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @TelegramMapping
    fun chatMessage(): Dispatcher = {
        message(Filter.Text and Filter.Private) {
            scope.launch {
                val chatId = message.chat.id
                completingService.processClient(
                    id = chatId,
                    args = message.text!!,
                    sendTypingAction = { bot.sendChatAction(ChatId.fromId(chatId), ChatAction.TYPING) },
                    sendResult = { completion ->
                        bot.sendMessage(
                            ChatId.fromId(chatId),
                            completion,
                            ParseMode.MARKDOWN
                        )
                    }
                )
            }
        }

        message(VoiceFilter and Filter.Private) {
            scope.launch {
                val chatId = message.chat.id
                transcriptionService.requestTranscriptionMode(
                    messageId = message.messageId,
                    chatId = message.chat.id,
                    sendText = { text -> bot.sendMessage(ChatId.fromId(chatId), text) },
                    sendTextReplyWithMarkup = { text, replyMessageId, markup ->
                        bot.sendMessage(
                            ChatId.fromId(chatId),
                            text,
                            replyToMessageId = replyMessageId,
                            replyMarkup = markup,
                        )
                    },
                )
            }
        }

        callbackQuery(CallbackData.ONLY_TRANSCRIBE.toString()) {
            scope.launch {
                callbackQuery.message?.replyToMessage?.let { message ->
                    message.voice?.let { voice ->
                        val chatId = message.chat.id
                        transcriptionService.processOnlyTranscription(
                            message.messageId,
                            voice.fileId,
                            sendText = { text -> bot.sendMessage(ChatId.fromId(chatId), text) },
                            downloadFileBytes = bot::downloadFileBytes,
                        )
                    }
                }
            }
        }

        callbackQuery(CallbackData.TRANSCRIBE_AND_RESPONSE.toString()) {
            scope.launch {
                callbackQuery.message?.replyToMessage?.let { message ->
                    message.voice?.let { voice ->
                        val chatId = message.chat.id
                        transcriptionService.processClient(
                            chatId = chatId,
                            voiceFileId = voice.fileId,
                            sendTypingAction = { bot.sendChatAction(ChatId.fromId(chatId), ChatAction.TYPING) },
                            sendText = { text -> bot.sendMessage(ChatId.fromId(chatId), text) },
                            sendTextWithDisabledNotification = { text ->
                                bot.sendMessage(
                                    ChatId.fromId(chatId),
                                    text,
                                    disableNotification = true
                                )
                            },
                            downloadFileBytes = bot::downloadFileBytes,
                        )
                    }
                }
            }
        }
    }
}