package com.hsworms_project.dd_assist.feature_note.note_presentation.notes

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hsworms_project.dd_assist.feature_note.note_presentation.note_util.Screen
import com.hsworms_project.dd_assist.feature_note.note_presentation.notes.note_components.NoteItem
import com.hsworms_project.dd_assist.feature_note.note_presentation.notes.note_components.OrderSection
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotesScreen (
    navController: NavController,
    vieModel: NotesViemodel = hiltViewModel()
) {
    val state = vieModel.state.value
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    val scope = rememberCoroutineScope()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(Screen.AddEditNoteScrenn.route) },
                Modifier.background(Color.Green)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Notiz hinzufügen")
            }
        },
        topBar = {},
        snackbarHost = { SnackbarHost(snackbarHostState)},

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Deine Notizen",
                    style = MaterialTheme.typography.titleMedium
                )
                IconButton(
                    onClick = {
                        vieModel.onEvent(NotesEvent.ToggleOrderSection)
                              },
                    ) {
                    Icon(
                        imageVector = Icons.Default.List,
                        contentDescription = "Sortiren"
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisable,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically()
            ) {
               OrderSection(
                   modifier = Modifier
                       .fillMaxWidth()
                       .padding(vertical = 16.dp),
                   noteOrder = state.noteOrder,
                   onOrderChange = {
                       vieModel.onEvent(NotesEvent.Order(it))
                   }
               )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                modifier = Modifier.fillMaxSize()) {
                items(state.notes) {note ->
                    NoteItem(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate(Screen.AddEditNoteScrenn.route + "?noteId=${note.id}") },
                        onDeleteClick = {
                            vieModel.onEvent(NotesEvent.DeleteNote(note))
                            scope.launch { 
                                val result = snackbarHostState.showSnackbar(
                                    message = "Notiz gelöscht",
                                    actionLabel = "Wiederherstellen"
                                )
                                if(result == SnackbarResult.ActionPerformed) {
                                    vieModel.onEvent(NotesEvent.RestoreNote)
                                }
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}
