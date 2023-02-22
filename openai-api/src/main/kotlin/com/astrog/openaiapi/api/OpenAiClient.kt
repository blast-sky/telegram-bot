package com.astrog.openaiapi.api

import com.astrog.openaiapi.internal.model.CompletingRequest
import com.astrog.openaiapi.internal.model.CompletingResponse
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class OpenAiClient(
    private val openAiRestTemplate: RestTemplate,
) {

    fun requestComplete(text: String): String {
        val response = openAiRestTemplate.postForObject(
            "v1/completions",
            CompletingRequest(prompt = text),
            CompletingResponse::class.java,
        ) ?: throw RuntimeException("Openai completions error")

        return response.choices.joinToString("\n") { it.text }
    }
}