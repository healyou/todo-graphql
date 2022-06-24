package ru.lappi.todographql

import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import ru.lappi.todographql.TodoGraphqlApplication

class ServletInitializer : SpringBootServletInitializer() {
    override fun configure(application: SpringApplicationBuilder): SpringApplicationBuilder {
        return application.sources(TodoGraphqlApplication::class.java)
    }
}