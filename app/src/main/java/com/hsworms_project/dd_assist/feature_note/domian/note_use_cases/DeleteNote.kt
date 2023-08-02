package com.hsworms_project.dd_assist.feature_note.domian.note_use_cases

import com.hsworms_project.dd_assist.classes.Note
import com.hsworms_project.dd_assist.feature_note.domian.note_repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note){
        repository.deleteNote(note)
    }
}