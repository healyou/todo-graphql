package ru.lappi.todographql.entity

import java.util.*

class NoteJsonDTO {
    var id: Long? = null
    var prev_note_version_id: Long? = null
    var guid: String? = null
    var version: Int? = null
    var title: String? = null
    var text: String? = null
    var user_id: Long? = null
    var create_date: Date? = null
    var deleted: Boolean? = null
    var archive: Boolean? = null
    var actual: Boolean? = null
    var note_files: List<NoteFileJsonDTO>? = null
}