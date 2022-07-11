package ru.lappi.todographql.entity

import com.fasterxml.jackson.annotation.JsonProperty

class UserJsonDTO {
    @JsonProperty("userId")
    var user_id: Long? = null
    @JsonProperty("privilegeCodes")
    var privilege_codes: List<String>? = null
}