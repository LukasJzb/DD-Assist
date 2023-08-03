package com.hsworms_project.dd_assist.feature_note.domian.note_use_cases

import com.hsworms_project.dd_assist.classes.Note
import com.hsworms_project.dd_assist.feature_note.domian.note_repository.NoteRepository
import com.hsworms_project.dd_assist.feature_note.domian.note_util.NoteOrder
import com.hsworms_project.dd_assist.feature_note.domian.note_util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetNotes(
    private val repository: NoteRepository
) {
    operator fun invoke(
        noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
    ): Flow<List<Note>> {
        return repository.getNeotes().map { notes ->
            when (noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedBy { it.titel.lowercase() }
                        is NoteOrder.Date -> notes.sortedBy { it.time }
                        is NoteOrder.Id -> notes.sortedBy { it.id }
                    }
                }

                is OrderType.Descending -> {
                    when (noteOrder) {
                        is NoteOrder.Title -> notes.sortedByDescending { it.titel.lowercase() }
                        is NoteOrder.Date -> notes.sortedByDescending { it.time }
                        is NoteOrder.Id -> notes.sortedByDescending { it.id }
                    }
                }
            }
        }
    }
}