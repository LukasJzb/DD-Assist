package com.hsworms_project.dd_assist.feature_note.note_presentation.add_edit_note

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.hsworms_project.dd_assist.R
import com.hsworms_project.dd_assist.feature_note.note_presentation.add_edit_note.add_edit_components.TransparentTippTextField
import com.hsworms_project.dd_assist.ui.theme.whiteback
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditNoteScreen(
    navController: NavController,
    viewModell: AddEditNoteViewModell = hiltViewModel()
) {
    val titelState = viewModell.noteTitel.value
    val inhaltState = viewModell.noteInhalt.value
    val snackbarState = remember {
        SnackbarHostState() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModell.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewModell.UiEvent.ShowSnackbar -> {
                    snackbarState.showSnackbar(
                        message = event.message
                    )
                }

                is AddEditNoteViewModell.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModell.onEvnet(AddEditNoteEvent.SaveNote)
            },
                containerColor = whiteback
            ) {
               Icon(imageVector = Icons.Default.Done, contentDescription = "Notiz Speichern")
            }
        },
        topBar = {},
        snackbarHost = { SnackbarHost(snackbarState) },
    ){
        Box {
            Image(painterResource(
                id = R.drawable.background),
                contentDescription = "Hintergrund",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.matchParentSize()
                )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            TransparentTippTextField(
                text = titelState.text,
                tipp = titelState.tipp,
                onValueChange = {
                    viewModell.onEvnet(AddEditNoteEvent.EnterdTitel(it))
                },
                onFocusChange = {
                    viewModell.onEvnet(AddEditNoteEvent.ChangeTitelFocus(it))
                },
                istTippSichtbar = titelState.istTippSichtbar,
                textStyle = MaterialTheme.typography.titleLarge,
            )
            Spacer(modifier = Modifier.height(20.dp))
            TransparentTippTextField(
                text = inhaltState.text,
                tipp = inhaltState.tipp,
                onValueChange = {
                    viewModell.onEvnet(AddEditNoteEvent.EnterdInhalt(it))
                },
                onFocusChange = {
                    viewModell.onEvnet(AddEditNoteEvent.ChangeInhaltFocus(it))
                },
                istTippSichtbar = inhaltState.istTippSichtbar,
                textStyle = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        }
        }
    }
