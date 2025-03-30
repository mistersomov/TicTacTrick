package com.mistersomov.tictactrick.presentation.screen.match.view.board

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import com.mistersomov.core.ui_kit.TicTacTrickTheme
import com.mistersomov.tictactrick.domain.entity.MatchStatus.Victory
import com.mistersomov.tictactrick.presentation.R
import com.mistersomov.tictactrick.presentation.extension.MultiPreview
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Intent
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Intent.Move
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.State
import com.mistersomov.tictactrick.presentation.screen.match.entity.board.CellUiEntity

@Composable
internal fun Board(
    viewState: State,
    sendIntent: (Intent) -> Unit,
) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center,
    ) {
        val gridSize = viewState.boardMode.value
        val boardSize = maxWidth.coerceAtMost(maxHeight) - 16.dp
        val cellSize = boardSize / gridSize

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            for (i in viewState.cells.indices step gridSize) {
                Row(modifier = Modifier.height(cellSize)) {
                    for (j in 0 until gridSize) {
                        val index = i + j

                        BoardCell(
                            entity = viewState.cells[index],
                            cellSize = cellSize,
                            isWinningCell = if (viewState.matchStatus is Victory) {
                                index in viewState.matchStatus.combination
                            } else {
                                false
                            },
                            onClick = { sendIntent(Move(it)) },
                        )
                    }
                }
            }
        }
    }
}

@MultiPreview
@Composable
private fun BoardPreview() {
    val viewState = State(
        cells = buildList {
            repeat(16) {
                add(
                    CellUiEntity(
                        0,
                        imageRes = R.drawable.cell,
                        imageDescription = R.string.empty,
                    )
                )
            }
        }
    )
    TicTacTrickTheme {
        Board(viewState) { }
    }
}