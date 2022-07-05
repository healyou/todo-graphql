package ru.lappi.todographql.graphql

import graphql.ExecutionInput
import graphql.spring.web.servlet.ExecutionInputCustomizer
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.web.context.request.WebRequest
import ru.lappi.todographql.properties.ApiProperties
import java.util.concurrent.CompletableFuture

@Component
@Primary
class GraphQLExecutionInputCustomizer @Autowired constructor(
    apiProperties: ApiProperties
) : ExecutionInputCustomizer {
    private val accessTokenHeaderCode: String

    init {
        accessTokenHeaderCode = apiProperties.accessTokenHeaderCode!!
    }

    override fun customizeExecutionInput(
        executionInput: ExecutionInput,
        webRequest: WebRequest
    ): CompletableFuture<ExecutionInput> {
        val token = webRequest.getHeader(accessTokenHeaderCode)
        executionInput.graphQLContext.put(accessTokenHeaderCode, token)
        return CompletableFuture.completedFuture(executionInput)
    }
}