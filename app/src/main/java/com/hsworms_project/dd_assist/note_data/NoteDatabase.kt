package com.hsworms_project.dd_assist.note_data

import androidx.room.*
import com.hsworms_project.dd_assist.classes.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase: RoomDatabase() {
    abstract val dao: NoteDao
}