package com.mistersomov.tictactrick.presentation.screen.main_menu

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.mistersomov.core.ui_kit.TicTacTrickTheme
import com.mistersomov.tictactrick.presentation.R
import com.mistersomov.tictactrick.presentation.common.GeneralButton
import com.mistersomov.tictactrick.presentation.extension.MultiPreview

@Composable
fun MainMenuScreen(
    onPlayClick: () -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        GeneralButton(
            text = stringResource(R.string.main_menu_play),
            onClick = onPlayClick,
        )
        GeneralButton(
            text = stringResource(R.string.main_menu_settings),
            onClick = {},
        )
        GeneralButton(
            text = stringResource(R.string.main_menu_quit),
            onClick = { (context as? Activity)?.finish() },
        )
    }
}

@MultiPreview
@Composable
private fun MainMenuScreenPreview() {
    TicTacTrickTheme {
        MainMenuScreen(
            onPlayClick = {},
        )
    }
}