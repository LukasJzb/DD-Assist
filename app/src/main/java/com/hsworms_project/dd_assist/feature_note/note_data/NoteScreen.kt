package com.hsworms_project.dd_assist.note_data

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    state: NoteState,
    onEvent: (NoteEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.1f)),
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(NoteEvent.ShowDialog)
            }) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = "Notiz hinzufügen"
                )
            }
        }
    ) {padding ->
        if (state.addingNote) {
            AddNoteDialog(state = state, onEvent = onEvent)
        }
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.1f)),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        )
        {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth().background(Color.Black.copy(alpha = 0.1f))
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SortType.values().forEach { sortType ->
                        Row(
                            modifier = Modifier
                                .clickable {
                                    onEvent(NoteEvent.SortNotes(sortType))
                                },
                            verticalAlignment = CenterVertically
                        ) {
                            RadioButton(
                                selected = state.sortType == sortType,
                                onClick = {
                                    onEvent(NoteEvent.SortNotes(sortType))
                                }
                            )
                            Text(text = sortType.name)
                        }
                    }
                }
            }
            items(state.note) { note ->
                Row(
                    modifier = Modifier.fillMaxWidth().background(Color.Black.copy(alpha = 0.1f))
                ) {
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "${note.titel}",
                            fontSize = 24.sp
                        )
                        Text(text = "${note.inhalt}",
                            fontSize = 16.sp
                        )
                    }
                    IconButton(onClick = {
                        onEvent(NoteEvent.DeleteNote(note))
                    }) {
                        Icon(imageVector = Icons.Default.Delete,
                            contentDescription = "Notiz löschen"
                        )
                    }

                }

            }
        }

    }
}