package com.hsworms_project.dd_assist.feature_note.note_presentation.add_edit_note


import androidx.compose.ui.focus.FocusState

sealed class AddEditNoteEvent{
    data class EnterdTitel(val value: String): AddEditNoteEvent()
    data class ChangeTitelFocus(val focusState: FocusState): AddEditNoteEvent()
    data class EnterdInhalt(val value: String): AddEditNoteEvent()
    data class ChangeInhaltFocus(val focusState: FocusState): AddEditNoteEvent()
    object SaveNote: AddEditNoteEvent()
}
