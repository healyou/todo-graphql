package ru.lappi.todographql.rest

import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.client.DefaultResponseErrorHandler
import java.io.IOException

class NoErrorResponseHandler : DefaultResponseErrorHandler() {
    @Throws(IOException::class)
    override fun hasError(response: ClientHttpResponse): Boolean {
        return false
    }

    override fun hasError(statusCode: HttpStatus): Boolean {
        return false
    }

    override fun hasError(unknownStatusCode: Int): Boolean {
        return false
    }
}