package com.astrog.telegramcommon.internal.client.configuration

import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Path

interface TelegramFileApiService {

    @GET("{filePath}")
    suspend fun downloadFile(
        @Path("filePath") filePath: String,
    ): Response
}