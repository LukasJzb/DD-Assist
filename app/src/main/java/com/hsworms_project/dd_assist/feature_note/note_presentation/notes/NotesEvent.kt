package com.hsworms_project.dd_assist.feature_note.note_presentation.notes

import com.hsworms_project.dd_assist.classes.Note
import com.hsworms_project.dd_assist.feature_note.domian.note_util.NoteOrder

sealed class NotesEvent {
    data class Order(val noteOrder: NoteOrder): NotesEvent()
    data class DeleteNote(val note: Note): NotesEvent()
    object RestoreNote: NotesEvent()
    object ToggleOrderSection: NotesEvent()
}