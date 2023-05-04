package com.astrog.telegramcommon.internal.client.configuration

import com.astrog.telegramcommon.domain.model.File
import com.astrog.telegramcommon.domain.model.Update
import com.astrog.telegramcommon.domain.model.UpdateContent
import com.astrog.telegramcommon.internal.client.ResponseDto
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TelegramApiService {

    @GET("getUpdates")
    suspend fun getUpdates(
        @Query("offset") offset: Long,
        @Query("timeout") timeout: Int,
    ): ResponseDto<List<Update>>

    @POST("getFile")
    @FormUrlEncoded
    suspend fun getFile(
        @Field("file_id") fileId: String,
    ): ResponseDto<File>

    @POST("sendMessage")
    @FormUrlEncoded
    suspend fun sendMessage(
        @Field("chat_id") chatId: Long,
        @Field("text") text: String,
        @Field("reply_to_message_id") replyToMessageId: Long?,
    ): ResponseDto<UpdateContent.Message>

    @POST("sendPhoto")
    @FormUrlEncoded
    suspend fun sendPhoto(
        @Field("chat_id") chatId: Long,
        @Field("photo") url: String,
    ): ResponseDto<UpdateContent.Message>
}