package ru.lappi.todographql.properties

import javax.validation.constraints.NotNull

class NotesApi {
    var baseUrl: @NotNull String? = null
    var path: @NotNull NotesPath? = null

    class NotesPath {
        var base: @NotNull String? = null
        var userNotes: @NotNull String? = null
    }
}