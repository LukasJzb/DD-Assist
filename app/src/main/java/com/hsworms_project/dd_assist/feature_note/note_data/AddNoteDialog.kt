package com.hsworms_project.dd_assist.note_data

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteDialog (
    state: NoteState,
    onEvent: (NoteEvent) -> Unit,
    modifier: Modifier = Modifier
) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = {
                onEvent(NoteEvent.HideDialog)
            },
            title = { Text(text = "Notiz hinzufügen") },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextField(
                        value = state.titel,
                        onValueChange = {
                            onEvent(NoteEvent.SetTitel(it))
                        },
                        placeholder = {
                            Text(text = "Titel der Notiz")
                        }
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(modifier = Modifier
                            .width(450.dp)
                            .wrapContentHeight()) {
                            TextField(
                                value = state.inhalt,
                                onValueChange = {
                                    onEvent(NoteEvent.SetInhalt(it))
                                },
                                placeholder = {
                                    Text(text = "Inhalt der Notiz")
                                }
                            )
                        }
                    }
                }
            },
            confirmButton = {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Button(onClick = {
                        onEvent(NoteEvent.SaveNote)
                    }) {
                        Text(text = "Notiz speichern")
                    }
                }
            })
    }
