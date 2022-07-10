package ru.lappi.todographql.utils

import com.github.tomakehurst.wiremock.client.WireMock.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.core.env.get
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import org.springframework.http.*
import org.springframework.stereotype.Component
import org.springframework.util.FileCopyUtils
import ru.lappi.todographql.properties.ApiProperties
import ru.lappi.todographql.rest.NoStatusErrorRestTemplate
import java.io.IOException
import java.io.InputStreamReader
import java.io.UncheckedIOException

@Component
class CommonUtils @Autowired constructor(
    private val restTemplate: NoStatusErrorRestTemplate,
    private val resourceLoader: ResourceLoader,
    private val environment: Environment,
    apiProperties: ApiProperties
) {
    private val userNotesPath: String
    private val userDataPath: String
    private val accessTokenHeaderCode: String

    init {
        userNotesPath = apiProperties.getUserNotesPath
        userDataPath = apiProperties.getUserDataPath
        accessTokenHeaderCode = apiProperties.accessTokenHeaderCode!!
    }

    fun readFileToString(path: String): String {
        val resource: Resource = resourceLoader.getResource(path)
        return asString(resource)
    }

    fun stubOkGetUserNotes(response: String) {
        stubFor(
            any(urlEqualTo(userNotesPath)).willReturn(
                aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withStatus(HttpStatus.OK.value())
                    .withBody(response)
            )
        )
    }

    fun stubOkGetUserData(response: String) {
        stubFor(
            any(urlEqualTo(userDataPath)).willReturn(
                aResponse()
                    .withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                    .withStatus(HttpStatus.OK.value())
                    .withBody(response)
            )
        )
    }

    private fun asString(resource: Resource): String {
        try {
            InputStreamReader(resource.inputStream, "utf-8").use {
                    reader -> return FileCopyUtils.copyToString(reader)
            }
        } catch (e: IOException) {
            throw UncheckedIOException(e)
        }
    }

    fun graphqlPost(request: String): ResponseEntity<String> {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.add(accessTokenHeaderCode, "test")
        return restTemplate.postForEntity(
            getGraphqlServiceUrl(),
            HttpEntity(request, headers),
            String::class.java
        )
    }

    fun getGraphqlServiceUrl(): String {
        return "http://localhost:${environment["local.server.port"]}/graphql"
    }
}