package com.astrog.audioconverter

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import java.io.File
import kotlin.test.Ignore

internal class AudioConverterKtTest {

    private val bytes = File(
        AudioConverterKtTest::class.java
            .getResource("test.opus")
            ?.file
            ?: error("Have not resource test.opus")
    )

    @Disabled("Uses ffmpeg")
    @Test
    fun `convertOggToWav() Must not throw When bytes from ogg`() = runTest {
        convertOggToWav(bytes)
    }
}