package com.hsworms_project.dd_assist.note_data

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.twotone.Delete
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hsworms_project.dd_assist.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteScreen(
    state: NoteState,
    onEvent: (NoteEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
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
        Box(modifier = Modifier
            .fillMaxSize()) {
            Image(painterResource(id = R.drawable.background)  ,
                contentDescription = "Hintergrundbild",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
                )
            LazyColumn(
                contentPadding = padding,
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            )
            {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White.copy(alpha = 0.5f)),
                        horizontalArrangement = Arrangement.Center
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White.copy(alpha = 0.5f))
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "${note.titel}",
                                fontSize = 24.sp,
                                maxLines = 1
                            )
                            Text(
                                text = "${note.inhalt}",
                                fontSize = 16.sp,
                                maxLines = 5
                            )
                        }
                        IconButton(onClick = {
                            onEvent(NoteEvent.DeleteNote(note))
                        }) {
                            Icon(imageVector = Icons.TwoTone.Delete,
                                contentDescription = "Notiz löschen")
                        }

                    }

                }
            }
        }
    }
}