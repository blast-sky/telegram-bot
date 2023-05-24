package com.astrog.telegrambot.domain

import com.astrog.audioconverter.convertOggToWav
import com.astrog.openaiapi.api.OpenAiClient
import com.astrog.telegrambot.writeToFile
import com.astrog.telegramcommon.api.TelegramService
import com.astrog.telegramcommon.domain.model.ChatAction
import com.astrog.telegramcommon.domain.model.MessageParseMode
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
            telegramService.sendMessage(chatId, "Try later...")
        },
        block = {
            telegramService.sendChatAction(chatId, ChatAction.TYPING)
            val transcription = getTranscription(voiceFileId)
            deleteTempFiles(voiceFileId)

            val messageWithTranscription = telegramService.sendMessage(
                chatId,
                "_Вы сказали:_ ||$transcription||",
                parseMode = MessageParseMode.MARKDOWNV2,
                disableNotification = true,
            )

            telegramService.sendChatAction(chatId, ChatAction.TYPING)
            val completion = completingService.getChatCompletionAndStoreMessages(chatId, transcription)
            telegramService.sendMessage(chatId, completion, messageWithTranscription.messageId)
        },
    )

    suspend fun processOnlyTranscription(messageId: Long, chatId: Long, voiceFileId: String) = catching(
        onException = {
            deleteTempFiles(voiceFileId)
            telegramService.sendMessage(chatId, "Try later...")
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
}