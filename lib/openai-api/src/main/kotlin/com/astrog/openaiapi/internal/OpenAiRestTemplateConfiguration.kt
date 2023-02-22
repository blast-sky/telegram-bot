package com.astrog.openaiapi.internal

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import java.net.URI

@Configuration
class OpenAiRestTemplateConfiguration {

    @Bean("openAiRestTemplate")
    fun openAiRestTemplate(property: OpenAiProperty): RestTemplate {
        val baseUrlInterceptor = ClientHttpRequestInterceptor { request, body, execution ->
            val requestFactory = SimpleClientHttpRequestFactory()
            val modifiedUrl = URI(property.baseUrl + request.uri)
            val modifiedRequest = requestFactory.createRequest(modifiedUrl, request.method)
            request.headers.forEach { (name, values) -> modifiedRequest.headers[name] = values }
            execution.execute(modifiedRequest, body)
        }

        val authInterceptor = ClientHttpRequestInterceptor { request, body, execution ->
            request.headers["Authorization"] = "Bearer " + property.token
            return@ClientHttpRequestInterceptor execution.execute(request, body)
        }

        return RestTemplate().apply {
            interceptors.addAll(listOf(baseUrlInterceptor, authInterceptor))
        }
    }
}