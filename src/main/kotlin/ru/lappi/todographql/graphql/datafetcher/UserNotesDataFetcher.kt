package ru.lappi.todographql.graphql.datafetcher

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.GraphQLContext
import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.lappi.todographql.entity.NoteJsonDTO
import ru.lappi.todographql.properties.ApiProperties
import ru.lappi.todographql.service.NotesService
import java.util.*

@Component
class UserNotesDataFetcher @Autowired constructor(
    private val notesService: NotesService,
    apiProperties: ApiProperties
)  : DataFetcher<List<NoteJsonDTO>> {
    private val accessTokenHeaderCode: String

    init {
        accessTokenHeaderCode = apiProperties.accessTokenHeaderCode!!
    }

    @Throws(Exception::class)
    override fun get(environment: DataFetchingEnvironment): List<NoteJsonDTO> {
        val userId = environment.getArgument<String>("userId").toString().toLong()
        environment.graphQlContext.put("userId", userId)
        val context: GraphQLContext = environment.graphQlContext
        val token = context.get<String>(accessTokenHeaderCode)

        val notesJson = notesService.getNotesJsonByUserId(userId, token)

        return if (notesJson != null && notesJson == "null") {
            Collections.emptyList()
        } else {
            val mapper = ObjectMapper()
            mapper.readValue(
                notesJson!!,
                object : TypeReference<List<NoteJsonDTO>>() {}
            )
        }
    }
}