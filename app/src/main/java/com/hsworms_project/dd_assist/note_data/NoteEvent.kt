package com.hsworms_project.dd_assist.note_data

import com.hsworms_project.dd_assist.classes.Note

sealed interface NoteEvent {
    object SaveNote: NoteEvent
    data class SetTitel(val titel: String): NoteEvent
    data class SetInhalt(val inhalt: String): NoteEvent
    data class SetTime(val time: Long): NoteEvent
    object ShowDialog: NoteEvent
    object HideDialog: NoteEvent
    data class SortNotes(val sortType: SortType): NoteEvent
    data class DeleteNote(val note: Note): NoteEvent
}