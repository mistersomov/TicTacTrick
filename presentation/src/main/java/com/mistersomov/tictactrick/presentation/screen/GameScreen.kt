package com.mistersomov.tictactrick.presentation.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mistersomov.tictactrick.domain.entity.GameStatus
import com.mistersomov.tictactrick.presentation.R
import com.mistersomov.tictactrick.presentation.extension.MultiPreview
import com.mistersomov.tictactrick.presentation.view.Board
import com.mistersomov.tictactrick.presentation.view.GameContract.Effect.ShowDialog
import com.mistersomov.tictactrick.presentation.view.GameContract.Intent.Reset
import com.mistersomov.tictactrick.presentation.view.GameContract.Intent.StartGame
import com.mistersomov.tictactrick.presentation.view.GameDialog
import com.mistersomov.tictactrick.presentation.viewmodel.GameViewModel
import kotlinx.coroutines.delay

@Composable
fun GameScreen(viewModel: GameViewModel = viewModel(factory = GameViewModel.Factory)) {
    val viewState by viewModel.viewState.collectAsState()
    val sendIntent by remember { mutableStateOf(viewModel::sendIntent) }

    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) { sendIntent(StartGame) }
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ShowDialog -> {
                    delay(1500)
                    showDialog = true
                }
            }
        }
    }

    if (showDialog) {
        GameDialog(
            title = if (viewState.gameStatus is GameStatus.Victory) "Victory" else "Draw",
            onRestart = {
                sendIntent(Reset)
                showDialog = false
            }
        )
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(color = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, top = 32.dp, end = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    modifier = Modifier.border(1.dp, Color.Black),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        modifier = Modifier.size(96.dp),
                        painter = painterResource(R.drawable.cross),
                        contentDescription = stringResource(R.string.cross),
                    )
                    Text(text = "Player 1")
                }
                Column(
                    modifier = Modifier.border(1.dp, Color.Black),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        modifier = Modifier.size(96.dp),
                        painter = painterResource(R.drawable.zero),
                        contentDescription = stringResource(R.string.cross),
                    )
                    Text("Player 2")
                }
            }
            Board(viewState = viewState, sendIntent = sendIntent)
        }
    }
}

@MultiPreview
@Composable
private fun MatchScreenPreview() {
    GameScreen()
}