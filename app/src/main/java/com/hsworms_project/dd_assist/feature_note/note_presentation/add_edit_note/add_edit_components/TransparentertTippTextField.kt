package com.hsworms_project.dd_assist.feature_note.note_presentation.add_edit_note.add_edit_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.hsworms_project.dd_assist.ui.theme.whiteback

@Composable
fun TransparentTippTextField (
    text: String,
    tipp: String,
    modifier: Modifier = Modifier,
    istTippSichtbar: Boolean = true,
    onValueChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle(),
    singelLine: Boolean = false,
    onFocusChange: (FocusState) -> Unit
) {
    Box(modifier = modifier) {
        BasicTextField(value = text,
            onValueChange = onValueChange,
            singleLine = singelLine,
            textStyle = textStyle,
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged {
                    onFocusChange(it)
                }
                .background(whiteback)
        )
        if (istTippSichtbar) {
            Text(
                text = tipp,
                style = textStyle,
                color = Color.DarkGray)
        }
    }
}