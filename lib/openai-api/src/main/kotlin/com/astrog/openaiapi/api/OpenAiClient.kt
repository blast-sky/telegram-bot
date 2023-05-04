package com.astrog.openaiapi.api

import com.astrog.openaiapi.internal.OpenAiProperty
import com.astrog.openaiapi.internal.dto.Data
import com.astrog.openaiapi.internal.dto.ImageGenerationRequest
import com.astrog.openaiapi.internal.dto.ImageGenerationResponse
import com.astrog.openaiapi.internal.dto.chatcompletion.ChatCompletionRequest
import com.astrog.openaiapi.internal.dto.chatcompletion.ChatCompletionResponse
import com.astrog.openaiapi.internal.dto.chatcompletion.ChatMessage
import com.astrog.openaiapi.internal.dto.completion.CompletingRequest
import com.astrog.openaiapi.internal.dto.completion.CompletingResponse
import com.astrog.openaiapi.internal.dto.transcription.TranscriptionResponse
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForObject
import java.io.File

@Service
class OpenAiClient(
    private val openAiRestTemplate: RestTemplate,
    private val openAiProperty: OpenAiProperty,
) {

    fun getCompletion(text: String): String {
        return openAiRestTemplate
            .postForObject<CompletingResponse>(
                "v1/completions",
                CompletingRequest(
                    model = openAiProperty.completionModel,
                    prompt = text,
                ),
            )
            .choices
            .joinToString("\n") { it.text }
    }

    fun getChatCompletion(chatMessages: List<ChatMessage> = emptyList()): ChatMessage {
        return openAiRestTemplate
            .postForObject<ChatCompletionResponse>(
                "v1/chat/completions",
                ChatCompletionRequest(
                    model = openAiProperty.chatCompletionModel,
                    messages = chatMessages,
                ),
            )
            .choices
            .first()
            .message
    }

    fun getImageCreation(text: String): List<String> {
        return openAiRestTemplate
            .postForObject<ImageGenerationResponse>(
                "v1/images/generations",
                ImageGenerationRequest(prompt = text),
            )
            .data
            .map(Data::url)
    }

    fun getTranslation(file: File): String {
        val headers = HttpHeaders().apply { contentType = MediaType.MULTIPART_FORM_DATA }
        val body = LinkedMultiValueMap<String, Any>().apply {
            add("file", FileSystemResource(file))
            add("model", "whisper-1")
        }

        val requestEntity = HttpEntity(body, headers)

        return openAiRestTemplate
            .postForObject<TranscriptionResponse>(
                "v1/audio/transcriptions",
                requestEntity,
            )
            .text ?: ""
    }
}