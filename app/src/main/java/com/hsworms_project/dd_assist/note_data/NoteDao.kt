package com.hsworms_project.dd_assist.note_data

import androidx.room.*
import com.hsworms_project.dd_assist.classes.Note
import kotlinx.coroutines.flow.Flow


@Dao
interface NoteDao {
    @Upsert
    suspend fun insertNote(note: Note)
    @Delete
    suspend fun deleteNote(note: Note)
    @Query("SELECT * FROM note ORDER BY titel ASC")
    fun getNotesOrderdByAlphapet(): Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY time ASC")
    fun getNotesOrderdByTime(): Flow<List<Note>>

    @Query("SELECT * FROM note ORDER BY id ASC")
    fun getNotesOrderdById(): Flow<List<Note>>
}