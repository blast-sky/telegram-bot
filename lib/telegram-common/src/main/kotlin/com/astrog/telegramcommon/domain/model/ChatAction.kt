package com.astrog.telegramcommon.domain.model

import com.fasterxml.jackson.annotation.JsonProperty

enum class ChatAction {
    @JsonProperty("typing")
    TYPING,

    @JsonProperty("upload_photo")
    UPLOAD_PHOTO,

    @JsonProperty("record_video")
    RECORD_VIDEO,

    @JsonProperty("record_voice")
    RECORD_VOICE,
}