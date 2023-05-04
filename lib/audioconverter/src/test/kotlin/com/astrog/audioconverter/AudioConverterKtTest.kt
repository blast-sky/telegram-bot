package com.astrog.audioconverter

import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.io.File

internal class AudioConverterKtTest {

    private val bytes = File(
        AudioConverterKtTest::class.java
            .getResource("test.ogg")
            ?.file
            ?: error("Have not resource test.ogg")
    )

    @Test
    fun `convertOggToWav() Must not throw When bytes from ogg`() = runTest {
        convertOggToWav(bytes)
    }
}