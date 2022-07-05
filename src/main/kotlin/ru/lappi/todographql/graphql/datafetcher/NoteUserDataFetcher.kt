package ru.lappi.todographql.graphql.datafetcher

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.GraphQLContext
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.lappi.todographql.entity.UserJsonDTO
import ru.lappi.todographql.properties.ApiProperties
import ru.lappi.todographql.service.UserService

@Component
class NoteUserDataFetcher @Autowired constructor(
    private val userService: UserService,
    apiProperties: ApiProperties
) : DataFetcher<UserJsonDTO> {
    private val accessTokenHeaderCode: String

    init {
        accessTokenHeaderCode = apiProperties.accessTokenHeaderCode!!
    }

    @Throws(Exception::class)
    override fun get(environment: DataFetchingEnvironment): UserJsonDTO {
        val userId = environment.graphQlContext.get<Long>("userId")
        val context: GraphQLContext = environment.graphQlContext

        val authorByIdKey: String = environment.field.name + "_id_" + userId
        if (context.hasKey(authorByIdKey)) {
            println("fetch user (CACHE) by id $userId")
            return context.get(authorByIdKey)
        } else {
            println("fetch user (REQUEST) by id $userId")
            val token = context.get<String>(accessTokenHeaderCode)
            val userJson = userService.getUserJsonByUserId(userId, token)
            val mapper = ObjectMapper()

            val user = mapper.readValue(
                userJson!!,
                object : TypeReference<UserJsonDTO>() {}
            )
            context.put(authorByIdKey, user)
            return user
        }
    }
}