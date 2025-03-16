package com.mistersomov.tictactrick.presentation.screen.match

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.mistersomov.core.ui_kit.TicTacTrickTheme
import com.mistersomov.tictactrick.domain.entity.MatchStatus
import com.mistersomov.tictactrick.presentation.R
import com.mistersomov.tictactrick.presentation.common.DialogButton
import com.mistersomov.tictactrick.presentation.common.GameDialog
import com.mistersomov.tictactrick.presentation.extension.MultiPreview
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Effect.ShowDialog
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Intent.ActivateTrickyCard
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Intent.Restart
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Intent.StartGame
import com.mistersomov.tictactrick.presentation.screen.match.view.board.Board
import com.mistersomov.tictactrick.presentation.screen.match.view.tricky_card.TrickyCardGroup
import com.mistersomov.tictactrick.presentation.screen.match.viewmodel.MatchViewModel
import kotlinx.coroutines.delay

@Composable
fun MatchScreen(viewModel: MatchViewModel = hiltViewModel()) {
    val viewState by viewModel.viewState.collectAsState()
    val sendIntent by remember { mutableStateOf(viewModel::sendIntent) }

    var showDialog by remember { mutableStateOf(false) }

//    BackHandler {
//        viewModel.sendIntent(OnBackClicked)
//    }

    LaunchedEffect(Unit) { sendIntent(StartGame) }
    LaunchedEffect(viewModel.effect) {
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
            title = if (viewState.matchStatus is MatchStatus.Victory) "Victory" else "Draw",
            onRestart = {
                sendIntent(Restart)
                showDialog = false
            }
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 24.dp, top = 32.dp, end = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = if (viewState.isCrossMove) Color.Green else Color.Black,
                        shape = RoundedCornerShape(8.dp),
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    modifier = Modifier.size(72.dp),
                    painter = painterResource(R.drawable.cross),
                    contentDescription = stringResource(R.string.cross),
                )
                Text(text = "Player 1")
            }

            DialogButton(R.drawable.ic_baseline_refresh_24) { sendIntent(Restart) }

            Column(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = if (!viewState.isCrossMove) Color.Green else Color.Black,
                        shape = RoundedCornerShape(8.dp),
                    ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    modifier = Modifier.size(72.dp),
                    painter = painterResource(R.drawable.zero),
                    contentDescription = stringResource(R.string.cross),
                )
                Text("Player 2")
            }
        }
        Board(
            viewState = viewState,
            sendIntent = sendIntent,
        )
        TrickyCardGroup(
            cards = viewState.trickyCards,
            onCardClick = { sendIntent(ActivateTrickyCard(it)) }
        )
    }
}

@MultiPreview
@Composable
private fun MatchScreenPreview() {
    TicTacTrickTheme {
        MatchScreen()
    }
}