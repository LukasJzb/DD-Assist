package com.hsworms_project.dd_assist.feature_note.note_presentation.note_util

sealed class Screen(val route: String) {
    object NoteScreen: Screen("notes_screen")
    object AddEditNoteScrenn: Screen("add_edit_note_screnn")
}
