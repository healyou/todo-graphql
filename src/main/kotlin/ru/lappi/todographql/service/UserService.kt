package ru.lappi.todographql.service

interface UserService {
    fun getUserJsonByUserId(userId: Long, token: String): String?
}