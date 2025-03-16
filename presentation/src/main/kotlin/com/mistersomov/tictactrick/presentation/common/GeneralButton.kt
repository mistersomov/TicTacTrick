package com.mistersomov.tictactrick.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.mistersomov.core.ui_kit.TicTacTrickTheme
import com.mistersomov.tictactrick.presentation.extension.PreviewPhone

@Composable
fun GeneralButton(
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(width = 200.dp, height = 56.dp)
            .padding(vertical = 4.dp)
            .background(
                brush = Brush.verticalGradient(listOf(Color(0xFFFCD446), Color(0xFFFEC433))),
                shape = RoundedCornerShape(8.dp),
            )
            .border(
                width = 2.dp,
                brush = Brush.verticalGradient(listOf(Color(0xFFFFFFFF), Color(0xFFFFF396))),
                shape = RectangleShape,
            )
            .clickable(
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = Color(0xFFFF7108),
        )
    }
}

@PreviewPhone
@Composable
private fun GeneralButtonPreview() {
    TicTacTrickTheme {
        GeneralButton("Text") { }
    }
}