package com.hsworms_project.dd_assist.feature_note.note_presentation.notes

import com.hsworms_project.dd_assist.classes.Note
import com.hsworms_project.dd_assist.feature_note.domian.note_util.NoteOrder
import com.hsworms_project.dd_assist.feature_note.domian.note_util.OrderType

data class NoteState(
    val notes: List<Note> = emptyList(),
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending),
    val isOrderSectionVisable: Boolean = true
)
