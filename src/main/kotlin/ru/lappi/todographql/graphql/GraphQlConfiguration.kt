package ru.lappi.todographql.graphql

import com.google.common.io.Resources
import graphql.GraphQL
import graphql.com.google.common.base.Charsets
import graphql.schema.GraphQLSchema
import graphql.schema.idl.RuntimeWiring
import graphql.schema.idl.SchemaGenerator
import graphql.schema.idl.SchemaParser
import graphql.schema.idl.TypeRuntimeWiring
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.lappi.todographql.graphql.datafetcher.NoteUserDataFetcher
import ru.lappi.todographql.graphql.datafetcher.UserNotesDataFetcher
import java.io.IOException

@Configuration
class GraphQlConfiguration {
    @Autowired
    var userNotesDataFetcher: UserNotesDataFetcher? = null
    @Autowired
    var noteUserDataFetcher: NoteUserDataFetcher? = null

    @Bean
    @Throws(IOException::class)
    fun graphQL(): GraphQL {
        val graphQLSchema = buildSchema(loadSdl())
        return GraphQL.newGraphQL(graphQLSchema).build()
    }

    private fun buildSchema(sdl: String): GraphQLSchema {
        val typeRegistry = SchemaParser().parse(sdl)
        val runtimeWiring = buildWiring()
        val schemaGenerator = SchemaGenerator()
        return schemaGenerator.makeExecutableSchema(typeRegistry, runtimeWiring)
    }

    private fun buildWiring(): RuntimeWiring {
        return RuntimeWiring.newRuntimeWiring()
            .type(createQueryTypeWiring())
            .type(createNoteTypeWiring())
            .build()
    }

    private fun createQueryTypeWiring(): TypeRuntimeWiring.Builder {
        return TypeRuntimeWiring.newTypeWiring("Query")
            .dataFetcher("userNotes", userNotesDataFetcher)
    }

    private fun createNoteTypeWiring(): TypeRuntimeWiring.Builder {
        return TypeRuntimeWiring.newTypeWiring("Note")
            .dataFetcher("user", noteUserDataFetcher)
    }

    @Throws(IOException::class)
    private fun loadSdl(): String {
        val url = Resources.getResource("schema.graphqls")
        return Resources.toString(url, Charsets.UTF_8)
    }
}