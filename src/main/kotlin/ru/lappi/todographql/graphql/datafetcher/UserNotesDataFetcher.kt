package ru.lappi.todographql.graphql.datafetcher

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import org.springframework.stereotype.Component

@Component
class UserNotesDataFetcher : DataFetcher<Map<String, String>> {
    @Throws(Exception::class)
    override fun get(environment: DataFetchingEnvironment): Map<String, String> {
        return HashMap()
    }
}