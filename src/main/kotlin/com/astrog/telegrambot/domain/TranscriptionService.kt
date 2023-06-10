package com.astrog.telegrambot.domain

import com.astrog.audioconverter.convertOggToWav
import com.astrog.openaiapi.api.OpenAiClient
import com.astrog.telegrambot.tryLatter
import com.astrog.telegrambot.writeToFile
import com.astrog.telegramcommon.api.TelegramService
import com.astrog.telegramcommon.domain.model.ChatAction
import com.astrog.telegramcommon.domain.model.markup.InlineKeyboardButton
import com.astrog.telegramcommon.domain.model.markup.InlineKeyboardMarkup
import com.astrog.telegramcommon.domain.model.update.CallbackQuery
import mu.KotlinLogging
import org.springframework.stereotype.Service
import java.io.File

private val logger = KotlinLogging.logger { }

@Service
class TranscriptionService(
    private val openAiClient: OpenAiClient,
    private val telegramService: TelegramService,
    private val completingService: CompletingService,
) : BaseCatchingService() {

    suspend fun process(chatId: Long, voiceFileId: String) = catching(
        onException = {
            deleteTempFiles(voiceFileId)
            telegramService.sendMessage(chatId, tryLatter)
        },
        block = {
            telegramService.sendChatAction(chatId, ChatAction.TYPING)
            val transcription = getTranscription(voiceFileId)
            deleteTempFiles(voiceFileId)

            val messageWithTranscription = telegramService.sendMessage(
                chatId,
                "Вы сказали: $transcription",
                disableNotification = true,
            )
            telegramService.sendChatAction(chatId, ChatAction.TYPING)
            val completion = completingService.getChatCompletionAndStoreMessages(chatId, transcription)
            telegramService.sendMessage(chatId, completion, messageWithTranscription.messageId)
        },
    )

    suspend fun requestTranscriptionMode(messageId: Long, chatId: Long) = catching(
        onException = {
            telegramService.sendMessage(chatId, tryLatter)
        },
        block = {
            telegramService.sendMessage(
                chatId,
                text = "Выберите эффект.",
                replyToMessageId = messageId,
                replyMarkup = InlineKeyboardMarkup(
                    listOf(
                        listOf(
                            InlineKeyboardButton(
                                text = "Только расшифровка",
                                callbackData = CallbackData.ONLY_TRANSCRIBE.toString(),
                            )
                        ),
                        listOf(
                            InlineKeyboardButton(
                                text = "Еще ответить",
                                callbackData = CallbackData.TRANSCRIBE_AND_RESPONSE.toString(),
                            ),
                        ),
                    ),
                ),
            )
        },
    )

    suspend fun processOnlyTranscription(messageId: Long, chatId: Long, voiceFileId: String) = catching(
        onException = {
            deleteTempFiles(voiceFileId)
            telegramService.sendMessage(chatId, tryLatter)
        },
        block = {
            val transcription = getTranscription(voiceFileId)
            deleteTempFiles(voiceFileId)
            telegramService.sendMessage(chatId, transcription, messageId)
        }
    )

    suspend fun getTranscription(voiceFileId: String): String {
        val voiceBytes = telegramService.downloadFile(voiceFileId)
        val convertedFile = convertOggByteArrayToWavFile(voiceBytes, voiceFileId)
        return openAiClient.getTranslation(convertedFile)
    }

    suspend fun convertOggByteArrayToWavFile(oggByteArray: ByteArray, fileId: String): File {
        val fileName = "$fileId.opus"
        val voiceFile = writeToFile(fileName, oggByteArray)
        return convertOggToWav(voiceFile)
    }

    fun deleteTempFiles(fileId: String) {
        val filesToDelete = listOf(
            "$fileId.opus",
            "$fileId.wav",
        ).map(::File)

        filesToDelete
            .filter(File::exists)
            .forEach(File::delete)
    }

    suspend fun choiceMode(callbackQuery: CallbackQuery) {
        callbackQuery.message?.let { message ->
            message.replyToMessage?.voice?.fileId?.let { voiceFileId ->
                when (callbackQuery.data) {
                    CallbackData.ONLY_TRANSCRIBE.toString() -> processOnlyTranscription(
                        messageId = message.messageId,
                        chatId = message.chat.id,
                        voiceFileId = voiceFileId,
                    )

                    CallbackData.TRANSCRIBE_AND_RESPONSE.toString() -> process(
                        chatId = message.chat.id,
                        voiceFileId = voiceFileId,
                    )
                }
            }
        }
    }
}