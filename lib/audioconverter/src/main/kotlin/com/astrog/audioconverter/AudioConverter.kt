package com.astrog.audioconverter

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.io.File

// used ffmpeg to convert files
suspend fun convertOggToWav(
    oggFile: File,
): File = withContext(Dispatchers.IO) {

    val wavFileName = "${oggFile.nameWithoutExtension}.wav"

    // ffmpeg -i test.opus -vn -acodec pcm_s16le file.wav
    val command = listOf(
        "ffmpeg",
        "-i",
        oggFile.name,
        "-vn",
        "-acodec",
        "pcm_s16le",
        wavFileName,
    )

    val process = ProcessBuilder(command).start()

    val outputReadJob = async(Dispatchers.IO) { process.inputStream.readBytes() }
    val errorReadJob = async(Dispatchers.IO) { process.errorStream.readBytes() }

    val returnCode = process.waitFor()

    val output = outputReadJob.await()
    val errors = errorReadJob.await()

    if (returnCode != 0) {
        error("Occur error when converting voice file. Output: <$output>. Error: <$errors>")
    }

    return@withContext File(wavFileName)
}