package com.mistersomov.tictactrick.presentation.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mistersomov.tictactrick.presentation.extension.MultiPreview
import com.mistersomov.tictactrick.presentation.view.Board
import com.mistersomov.tictactrick.presentation.view.GameContract.Intent.StartGame
import com.mistersomov.tictactrick.presentation.viewmodel.GameViewModel

@Composable
fun GameScreen(viewModel: GameViewModel = viewModel(factory = GameViewModel.Factory)) {
    val viewState by viewModel.viewState.collectAsState()
    val sendIntent by remember { mutableStateOf(viewModel::sendIntent) }

    LaunchedEffect(Unit) { sendIntent(StartGame) }

    Scaffold(containerColor = Color.White) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Board(viewState = viewState, sendIntent = sendIntent)
        }
    }
}

@MultiPreview
@Composable
private fun MatchScreenPreview() {
    GameScreen()
}