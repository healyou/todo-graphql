package ru.lappi.todographql.properties

import javax.validation.constraints.NotNull

class UsersApi {
    var baseUrl: @NotNull String? = null
    var path: @NotNull UsersPath? = null

    class UsersPath {
        var base: @NotNull String? = null
        var register: @NotNull String? = null
    }
}