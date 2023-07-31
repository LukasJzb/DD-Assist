package com.hsworms_project.dd_assist.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hsworms_project.dd_assist.note_data.NoteDao
import com.hsworms_project.dd_assist.note_data.NoteEvent
import com.hsworms_project.dd_assist.note_data.NoteState
import com.hsworms_project.dd_assist.note_data.SortType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class NoteViewmodel(
    private val dao: NoteDao
): ViewModel() {
    private val _sortType = MutableStateFlow(SortType.TITEL)
    private val _notes = _sortType
        .flatMapLatest {sortType ->
            when(sortType) {
                SortType.TITEL -> dao.getNotesOrderdByAlphapet()
                SortType.TIME -> dao.getNotesOrderdByTime()
                SortType.ID -> dao.getNotesOrderdById()
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    private val _state = MutableStateFlow(NoteState())
    val state = combine(_state,_sortType, _notes ) { state, sortType, notes ->
        state.copy(
            note = notes,
            sortType = sortType
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), NoteState())

    fun onEvent(event: NoteEvent) {
        when(event) {
            is NoteEvent.DeleteNote -> {
                viewModelScope.launch {
                    dao.deleteNote(event.note)
                }
            }
            NoteEvent.HideDialog -> {
                _state.update { it.copy(
                    addingNote = false
                ) }
            }
            NoteEvent.SaveNote -> {
                val titel = state.value.titel
                val inhalt = state.value.inhalt
                val time = state.value.time

                if(titel.isBlank() || inhalt.isBlank() || time == 0L) {
                    return
                }
                val note = Note(
                    titel = titel,
                    inhalt = inhalt,
                    time = System.currentTimeMillis()
                )
                viewModelScope.launch {
                    dao.insertNote(note)
                }
                _state.update { it.copy(
                    addingNote = false,
                    titel = "",
                    inhalt = "",
                    time = 0
                ) }
            }
            is NoteEvent.SetInhalt -> {
                _state.update { it.copy(
                    inhalt = event.inhalt
                ) }
            }
            is NoteEvent.SetTime -> {
                _state.update {
                    it.copy(
                        time = event.time
                    )
                }
            }
            is NoteEvent.SetTitel -> {
                _state.update { it.copy(
                    titel = event.titel
                ) }
            }
            NoteEvent.ShowDialog -> {
                _state.update { it.copy(
                    addingNote = true
                ) }
            }
            is NoteEvent.SortNotes -> {
                _sortType.value = event.sortType
            }

            else -> {}
        }
    }
}