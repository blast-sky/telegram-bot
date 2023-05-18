package com.astrog.telegramcommon.internal.client.configuration

import com.astrog.telegramcommon.internal.property.TelegramBotProperty
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

@Configuration
class TelegramRetrofitConfiguration(
    private val telegramBotProperty: TelegramBotProperty,
) {

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .connectTimeout(telegramBotProperty.longPollingTimeout + 5L, TimeUnit.SECONDS)
        .writeTimeout(telegramBotProperty.longPollingTimeout + 5L, TimeUnit.SECONDS)
        .readTimeout(telegramBotProperty.longPollingTimeout + 5L, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    private val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
        .client(client)

    @Bean
    fun telegramApiService(): TelegramApiService {
        return retrofitBuilder
            .baseUrl("${telegramBotProperty.baseUrlWithToken}/")
            .build()
            .create(TelegramApiService::class.java)
    }

    @Bean
    fun telegramFileApiService(): TelegramFileApiService {
        return retrofitBuilder
            .baseUrl("${telegramBotProperty.fileBaseUrlWithToken}/")
            .build()
            .create(TelegramFileApiService::class.java)
    }
}