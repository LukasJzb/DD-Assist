package com.hsworms_project.dd_assist.feature_note.domian.note_use_cases

import com.hsworms_project.dd_assist.classes.InvalidNoteExpectation
import com.hsworms_project.dd_assist.classes.Note
import com.hsworms_project.dd_assist.feature_note.domian.note_repository.NoteRepository

class AddNote(
    private val repository: NoteRepository
) {
    @Throws(InvalidNoteExpectation::class)
    suspend operator fun invoke(note: Note){
        if (note.titel.isBlank()) {
            throw InvalidNoteExpectation("Die Notiz hat keinen Titel.")
        }
        if(note.inhalt.isBlank()) {
            throw InvalidNoteExpectation("Die Notiz hat keinen Inhalt.")
        }
        repository.insertNote(note)
    }
}