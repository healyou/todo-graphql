package ru.lappi.todographql.properties

import javax.validation.constraints.NotNull

class External {
    var users: @NotNull UsersApi? = null
    var notes: @NotNull NotesApi? = null
}