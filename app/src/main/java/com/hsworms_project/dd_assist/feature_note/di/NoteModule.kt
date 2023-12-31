package com.hsworms_project.dd_assist.feature_note.di

import android.app.Application
import androidx.room.Room
import com.hsworms_project.dd_assist.feature_note.domian.note_repository.NoteRepository
import com.hsworms_project.dd_assist.feature_note.domian.note_use_cases.AddNote
import com.hsworms_project.dd_assist.feature_note.domian.note_use_cases.DeleteNote
import com.hsworms_project.dd_assist.feature_note.domian.note_use_cases.GetNote
import com.hsworms_project.dd_assist.feature_note.domian.note_use_cases.GetNotes
import com.hsworms_project.dd_assist.feature_note.domian.note_use_cases.NoteUseCases
import com.hsworms_project.dd_assist.feature_note.note_data.note_data_repo.NoteRepositoryImpl
import com.hsworms_project.dd_assist.feature_note.note_data.note_data_source.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {
    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application): NoteDatabase {
        return Room.databaseBuilder(
            app,
            NoteDatabase::class.java,
            NoteDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideNoteRepository(db: NoteDatabase): NoteRepository{
        return NoteRepositoryImpl(db.noteDao)
    }

    @Provides
    @Singleton
    fun provideNoteUSeCases(repository: NoteRepository): NoteUseCases {
        return NoteUseCases(
            getNotes = GetNotes(repository),
            deleteNote = DeleteNote(repository),
            addNote = AddNote(repository),
            getNote = GetNote(repository)
        )
    }
}