package ru.lappi.todographql.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotNull

@Validated
@Configuration
@ConfigurationProperties(prefix = "api")
class ApiProperties {
    var accessTokenHeaderCode: @NotNull String? = null
    var external: @NotNull External? = null

    val getUserDataUrl: String
        get() = this.external!!.users!!.baseUrl + this.external!!.users!!.path!!.userData

    val getUserNotesUrl: String
        get() = this.external!!.notes!!.baseUrl + this.external!!.notes!!.path!!.userNotes
}