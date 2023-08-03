package com.hsworms_project.dd_assist.feature_note.domian.note_use_cases

import com.hsworms_project.dd_assist.classes.Note
import com.hsworms_project.dd_assist.feature_note.domian.note_repository.NoteRepository

class GetNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int):Note?{
        return repository.getNoteById(id)
    }
}