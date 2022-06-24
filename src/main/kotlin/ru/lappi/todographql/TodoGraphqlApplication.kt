package ru.lappi.todographql

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TodoGraphqlApplication

fun main(args: Array<String>) {
	runApplication<TodoGraphqlApplication>(*args)
}
