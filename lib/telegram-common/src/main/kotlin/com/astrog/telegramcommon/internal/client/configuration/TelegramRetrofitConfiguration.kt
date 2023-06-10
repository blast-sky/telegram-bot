package com.astrog.telegramcommon.internal.client.configuration

import com.astrog.telegramcommon.internal.property.TelegramBotProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import mu.KotlinLogging
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

val logger = KotlinLogging.logger { }

@Configuration
class TelegramRetrofitConfiguration(
    private val telegramBotProperty: TelegramBotProperty,
) {

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor { logger.debug { it } }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .connectTimeout(telegramBotProperty.longPollingTimeout + 5L, TimeUnit.SECONDS)
        .writeTimeout(telegramBotProperty.longPollingTimeout + 5L, TimeUnit.SECONDS)
        .readTimeout(telegramBotProperty.longPollingTimeout + 5L, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()


    @Bean
    fun retrofitBuilder(mapper: ObjectMapper): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(JacksonConverterFactory.create(jacksonObjectMapper()))
            .client(client)
    }

    @Bean
    fun telegramApiService(retrofitBuilder: Retrofit.Builder): TelegramApiService {
        return retrofitBuilder
            .baseUrl(telegramBotProperty.baseUrlWithToken + '/')
            .build()
            .create(TelegramApiService::class.java)
    }

    @Bean
    fun telegramFileApiService(retrofitBuilder: Retrofit.Builder): TelegramFileApiService {
        return retrofitBuilder
            .baseUrl(telegramBotProperty.fileBaseUrlWithToken + '/')
            .build()
            .create(TelegramFileApiService::class.java)
    }
}