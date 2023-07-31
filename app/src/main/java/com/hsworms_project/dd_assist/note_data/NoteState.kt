package com.hsworms_project.dd_assist.note_data

import android.os.Parcel
import android.os.Parcelable
import com.hsworms_project.dd_assist.classes.Note

data class NoteState(
    val note: List<Note> = emptyList(),
    val titel: String = "",
    val inhalt: String = "",
    val time: Long = 0,
    val addingNote: Boolean = false,
    val sortType: SortType = SortType.TITEL
)
