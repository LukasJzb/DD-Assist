package com.hsworms_project.dd_assist.feature_note.domian.note_repository

import com.hsworms_project.dd_assist.classes.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepository {
    fun getNeotes(): Flow<List<Note>>

    suspend fun getNoteById(id: Int):Note?
    suspend fun insertNote(note: Note)
    suspend fun deleteNote(note: Note)
}