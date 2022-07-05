package ru.lappi.todographql.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import ru.lappi.todographql.properties.ApiProperties
import ru.lappi.todographql.rest.NoStatusErrorRestTemplate

@Component
class UserServiceImpl @Autowired constructor(
    private val restTemplate: NoStatusErrorRestTemplate,
    apiProperties: ApiProperties
) : UserService {
    private val accessTokenHeaderCode: String?
    private val userDataUrl: String

    init {
        accessTokenHeaderCode = apiProperties.accessTokenHeaderCode
        userDataUrl = apiProperties.getUserDataUrl
    }

    override fun getUserJsonByUserId(userId: Long, token: String): String? {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        headers.add(accessTokenHeaderCode!!, token)

        val map: MultiValueMap<String, String> = LinkedMultiValueMap()
        map.add("user_id", userId.toString())
        val request = HttpEntity(map, headers)

        val response = restTemplate.postForEntity(
            userDataUrl,
            request,
            String::class.java
        )
        return response.body
    }
}