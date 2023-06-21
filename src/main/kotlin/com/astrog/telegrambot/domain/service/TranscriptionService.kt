package com.astrog.telegrambot.domain.service

import com.astrog.audioconverter.convertOggToWav
import com.astrog.openaiapi.api.OpenAiClient
import com.astrog.telegrambot.tryLatter
import com.astrog.telegrambot.writeToFile
import com.github.kotlintelegrambot.entities.InlineKeyboardMarkup
import com.github.kotlintelegrambot.entities.ReplyMarkup
import com.github.kotlintelegrambot.entities.keyboard.InlineKeyboardButton
import org.springframework.stereotype.Service
import java.io.File

@Service
class TranscriptionService(
    private val openAiClient: OpenAiClient,
    private val completingService: CompletingService,
) : BaseCatchingService() {

    suspend fun processClient(
        chatId: Long,
        voiceFileId: String,
        sendTypingAction: () -> Unit,
        sendText: (String) -> Unit,
        sendTextWithDisabledNotification: (String) -> Unit,
        downloadFileBytes: (String) -> ByteArray?,
    ) = catching(
        onException = {
            deleteTempFiles(voiceFileId)
            sendText.invoke(tryLatter)
        },
        block = {
            sendTypingAction.invoke()
            val transcription = getTranscription(voiceFileId, downloadFileBytes)
            deleteTempFiles(voiceFileId)

            sendTextWithDisabledNotification("Вы сказали: $transcription")

            completingService.processClient(chatId, transcription, sendTypingAction, sendText)
        },
    )

    suspend fun requestTranscriptionMode(
        messageId: Long,
        chatId: Long,
        sendText: (String) -> Unit,
        sendTextReplyWithMarkup: (String, Long, ReplyMarkup) -> Unit,
    ) = catching(
        onException = {
            sendText.invoke(tryLatter)
        },
        block = {
            sendTextReplyWithMarkup.invoke(
                "Выберите эффект:",
                messageId,
                InlineKeyboardMarkup.create(
                    listOf(
                        listOf(
                            InlineKeyboardButton.CallbackData(
                                text = "Только расшифровка",
                                callbackData = CallbackData.ONLY_TRANSCRIBE.toString(),
                            )
                        ),
                        listOf(
                            InlineKeyboardButton.CallbackData(
                                text = "Расшифровать и ответить",
                                callbackData = CallbackData.TRANSCRIBE_AND_RESPONSE.toString(),
                            ),
                        ),
                    )
                ),
            )
        },
    )

    suspend fun processOnlyTranscription(
        chatId: Long,
        voiceFileId: String,
        sendText: (String) -> Unit,
        downloadFileBytes: (String) -> ByteArray?,
    ) = catching(
        onException = {
            deleteTempFiles(voiceFileId)
            sendText.invoke(tryLatter)
        },
        block = {
            val transcription = getTranscription(voiceFileId, downloadFileBytes)
            deleteTempFiles(voiceFileId)
            sendText.invoke(transcription)
        }
    )

    suspend fun getTranscription(voiceFileId: String, downloadFileBytes: (String) -> ByteArray?): String {
        val voiceBytes = downloadFileBytes(voiceFileId) ?: error("Voice file <$voiceFileId> is absent.")
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