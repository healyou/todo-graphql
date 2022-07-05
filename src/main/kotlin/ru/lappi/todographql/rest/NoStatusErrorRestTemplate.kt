package ru.lappi.todographql.rest

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class NoStatusErrorRestTemplate : RestTemplate() {
    init {
        requestFactory = HttpComponentsClientHttpRequestFactory()
        /* Все сообщения от сервиса не будут считаться ошибками response */
        errorHandler = NoErrorResponseHandler()
    }
}