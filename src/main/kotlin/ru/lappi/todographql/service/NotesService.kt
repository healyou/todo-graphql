package ru.lappi.todographql.service

interface NotesService {
    fun getNotesJsonByUserId(userId: Long, token: String): String?
}