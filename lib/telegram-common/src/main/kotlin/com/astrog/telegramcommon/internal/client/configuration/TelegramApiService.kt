package com.astrog.telegramcommon.internal.client.configuration

import com.astrog.telegramcommon.domain.model.File
import com.astrog.telegramcommon.domain.model.MessageParseMode
import com.astrog.telegramcommon.domain.model.update.Message
import com.astrog.telegramcommon.domain.model.update.RawUpdate
import com.astrog.telegramcommon.internal.client.TelegramResponse
import retrofit2.Response
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
    ): Response<TelegramResponse<List<RawUpdate>>>

    @POST("getFile")
    @FormUrlEncoded
    suspend fun getFile(
        @Field("file_id") fileId: String,
    ): Response<TelegramResponse<File>>

    @POST("sendMessage")
    @FormUrlEncoded
    suspend fun sendMessage(
        @Field("chat_id") chatId: Long,
        @Field("text") text: String,
        @Field("reply_to_message_id") replyToMessageId: Long?,
        @Field("parse_mode") parseMode: MessageParseMode?,
        @Field("disable_notification") disableNotification: Boolean?,
        @Field("allow_sending_without_reply") allowSendingWithoutReply: Boolean?
    ): Response<TelegramResponse<Message>>

    @POST("sendPhoto")
    @FormUrlEncoded
    suspend fun sendPhoto(
        @Field("chat_id") chatId: Long,
        @Field("photo") url: String,
    ): Response<TelegramResponse<Message>>
}