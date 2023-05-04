package com.astrog.telegrambot

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream


suspend fun writeToFile(fileName: String, byteArray: ByteArray): File = withContext(Dispatchers.IO) {
    FileOutputStream(fileName).use { stream -> stream.write(byteArray) }
    return@withContext File(fileName)
}