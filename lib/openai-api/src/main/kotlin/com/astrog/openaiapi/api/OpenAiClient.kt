package com.astrog.openaiapi.api

import com.astrog.openaiapi.internal.dto.*
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.postForObject

@Service
class OpenAiClient(
    private val openAiRestTemplate: RestTemplate,
) {

    fun getCompletion(text: String): String {
        return openAiRestTemplate
            .postForObject<CompletingResponse>(
                "v1/completions",
                CompletingRequest(prompt = text),
            )
            .choices
            .joinToString("\n") { it.text }
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
}