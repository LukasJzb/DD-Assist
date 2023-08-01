package com.hsworms_project.dd_assist.feature_note.note_data

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.hsworms_project.dd_assist.classes.NoteViewmodel
import com.hsworms_project.dd_assist.note_data.NoteEvent
import com.hsworms_project.dd_assist.note_data.NoteState

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    viewmodel: NoteViewmodel,
    state: NoteState,
    onEvent: (NoteEvent) -> Unit,
    modifier: Modifier = Modifier
) {

}