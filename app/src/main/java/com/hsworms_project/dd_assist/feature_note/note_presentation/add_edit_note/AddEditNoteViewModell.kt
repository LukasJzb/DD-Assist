package com.hsworms_project.dd_assist.feature_note.note_presentation.add_edit_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hsworms_project.dd_assist.classes.InvalidNoteExpectation
import com.hsworms_project.dd_assist.classes.Note
import com.hsworms_project.dd_assist.feature_note.domian.note_use_cases.NoteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModell @Inject constructor(
    private val noteUseCases: NoteUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel(){
    private val _noteTitel = mutableStateOf(NoteTextFieldState(
        tipp = "Titel eingeben..."
    ))
    val noteTitel: State<NoteTextFieldState> = _noteTitel

    private val _noteInhalt = mutableStateOf(NoteTextFieldState(
        tipp = "Inhalt der Notiz eingebne ..."
    ))
    val noteInhalt: State<NoteTextFieldState> = _noteInhalt

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var aktuelleId: Int? = null

    init {
        savedStateHandle.get<Int>("id")?.let { id ->
            if (id != -1) {
                viewModelScope.launch {
                    noteUseCases.getNote(id)?.also { note ->
                        aktuelleId = id
                        _noteTitel.value = noteTitel.value.copy(
                            text = note.titel,
                            istTippSichtbar = false
                        )
                        _noteInhalt.value = noteInhalt.value.copy(
                            text = note.inhalt,
                            istTippSichtbar = false
                        )
                    }
                }
            }
        }
    }

    fun onEvnet(event: AddEditNoteEvent) {
        when(event) {
            is AddEditNoteEvent.EnterdTitel -> {
                _noteTitel.value = noteTitel.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeTitelFocus -> {
                _noteTitel.value = noteTitel.value.copy(
                    istTippSichtbar = !event.focusState.isFocused &&
                    noteTitel.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.EnterdInhalt -> {
                _noteInhalt.value = noteInhalt.value.copy(
                    text = event.value
                )
            }
            is AddEditNoteEvent.ChangeInhaltFocus -> {
                _noteInhalt.value = _noteInhalt.value.copy(
                    istTippSichtbar = !event.focusState.isFocused &&
                    _noteInhalt.value.text.isBlank()
                )
            }
            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCases.addNote(
                            Note(
                                titel = noteTitel.value.text,
                                inhalt = noteInhalt.value.text,
                                time = System.currentTimeMillis(),
                                id = aktuelleId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: InvalidNoteExpectation) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.message ?: "Notiz konnte nicht\ngespeichert werden."
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String): UiEvent()
        object SaveNote: UiEvent()
    }
}