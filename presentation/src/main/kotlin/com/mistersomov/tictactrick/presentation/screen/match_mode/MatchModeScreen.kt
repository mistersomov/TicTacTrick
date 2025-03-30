package com.mistersomov.tictactrick.presentation.screen.match_mode

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.mistersomov.core.ui_kit.TicTacTrickTheme
import com.mistersomov.tictactrick.presentation.R
import com.mistersomov.tictactrick.presentation.common.GeneralButton
import com.mistersomov.tictactrick.presentation.extension.PreviewPhone

@Composable
fun MatchModeScreen(
    onClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        GeneralButton(
            text = stringResource(R.string.match_mode_4x4),
            onClick = { onClick(4) },
        )
    }
}

@PreviewPhone
@Composable
private fun MatchModeScreenPreview() {
    TicTacTrickTheme {
        MatchModeScreen {

        }
    }
}