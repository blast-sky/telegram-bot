package com.astrog.telegramcommon.internal.client.configuration

import retrofit2.http.GET
import retrofit2.http.Path

interface TelegramFileApiService {

    @GET("{filePath}")
    suspend fun downloadFile(
        @Path("filePath") filePath: String,
    ): ByteArray?
}