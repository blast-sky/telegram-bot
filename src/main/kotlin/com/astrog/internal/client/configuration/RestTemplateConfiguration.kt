package com.astrog.internal.client.configuration

import com.astrog.internal.property.TelegramBotProperty
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.ClientHttpRequestInterceptor
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import java.net.URI

@Configuration
class RestTemplateConfiguration {

    @Bean
    fun telegramRestTemplate(telegramBotProperty: TelegramBotProperty): RestTemplate {
        val interceptor = ClientHttpRequestInterceptor { request, body, execution ->
            val requestFactory = SimpleClientHttpRequestFactory()
            val modifiedUrl = URI(telegramBotProperty.baseUrlWithToken + request.uri)
            val modifiedRequest = requestFactory.createRequest(modifiedUrl, request.method)
            request.headers.forEach { (name, values) -> modifiedRequest.headers[name] = values }
            execution.execute(modifiedRequest, body)
        }

        return RestTemplate().apply {
            interceptors.add(interceptor)
        }
    }
}