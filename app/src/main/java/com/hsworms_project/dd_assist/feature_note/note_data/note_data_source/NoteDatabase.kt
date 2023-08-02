package com.hsworms_project.dd_assist.feature_note.note_data.note_data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hsworms_project.dd_assist.classes.Note

@Database(
    entities = [Note::class],
    version = 2
)
abstract class NoteDatabase: RoomDatabase() {

    abstract val noteDao: NoteDao

    companion object{
        const val DATABASE_NAME = "note_db"
    }
}