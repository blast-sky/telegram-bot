package com.astrog.telegrambot.api.openai

import com.astrog.telegrambot.domain.service.CompletingService
import com.astrog.telegrambot.domain.service.ImageGenerationService
import com.astrog.telegrambot.domain.service.TranscriptionService
import com.astrog.telegrambot.domain.openai.OpenAiMessageStore
import com.astrog.telegrambot.domain.telegram.TelegramConfiguration
import com.astrog.telegrambot.domain.telegram.TelegramMapping
import com.astrog.telegrambot.internal.bot.Dispatcher
import com.astrog.telegrambot.messageAreCleared
import com.astrog.telegrambot.messageMustReplyToMessageWithVoice
import com.github.kotlintelegrambot.dispatcher.command
import com.github.kotlintelegrambot.entities.ChatAction
import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.entities.TelegramFile
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

@TelegramConfiguration
class CommonOpenAiController(
    private val messageStore: OpenAiMessageStore,
    private val completingService: CompletingService,
    private val imageGenerationService: ImageGenerationService,
    private val transcriptionService: TranscriptionService,
) {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    @TelegramMapping
    fun commonCommands(): Dispatcher = {
        command("comp") {
            scope.launch {
                val chatId = message.chat.id
                completingService.processClient(
                    id = chatId,
                    args = args.joinToString(" "),
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

        command("ig") {
            scope.launch {
                val longId = message.chat.id
                val chatId = ChatId.fromId(longId)
                imageGenerationService.processClient(
                    chatId = longId,
                    text = args.joinToString(" "),
                    sendStartAction = { bot.sendChatAction(chatId, ChatAction.UPLOAD_PHOTO) },
                    sendImage = { url -> bot.sendPhoto(chatId, TelegramFile.ByUrl(url)) },
                    sendText = { text -> bot.sendMessage(chatId, text) },
                )
            }
        }

        command("clear") {
            scope.launch {
                messageStore.clearMessages(message.chat.id)
                bot.sendMessage(ChatId.fromId(message.chat.id), messageAreCleared)
            }
        }

        command("tran") {
            scope.launch {
                val replyVoice = message.replyToMessage?.voice
                if (replyVoice == null) {
                    bot.sendMessage(ChatId.fromId(message.chat.id), messageMustReplyToMessageWithVoice)
                    return@launch
                }
                val chatId = message.chat.id
                transcriptionService.processOnlyTranscription(
                    chatId = chatId,
                    voiceFileId = replyVoice.fileId,
                    sendText = { text -> bot.sendMessage(ChatId.fromId(chatId), text) },
                    downloadFileBytes = { fileId -> bot.downloadFileBytes(fileId) },
                )
            }
        }
    }
}