package com.mistersomov.tictactrick.presentation.screen.match.view.board

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.coerceAtMost
import androidx.compose.ui.unit.dp
import com.mistersomov.tictactrick.domain.entity.Cell
import com.mistersomov.tictactrick.domain.entity.CellType.CROSS
import com.mistersomov.tictactrick.domain.entity.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.CellType.ZERO
import com.mistersomov.tictactrick.domain.entity.MatchStatus.Victory
import com.mistersomov.tictactrick.presentation.extension.MultiPreview
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Intent
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Intent.Move
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.State

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
        val gridSize = viewState.fieldMode.value
        val boardSize = maxWidth.coerceAtMost(maxHeight) - 16.dp
        val cellSize = boardSize / gridSize

        Column(
            modifier = Modifier
                .size(boardSize)
                .border(
                    width = 4.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(8.dp),
                )
                .drawWithCache { onDrawBehind { drawBorders(gridSize, size.width) } },
        ) {
            for (i in viewState.cells.indices step gridSize) {
                Row(modifier = Modifier.height(cellSize)) {
                    for (j in 0 until gridSize) {
                        val index = i + j

                        BoardCell(
                            item = viewState.cells[index],
                            cellSize = cellSize,
                            isWinningCell = if (viewState.matchStatus is Victory) {
                                index in viewState.matchStatus.combination
                            } else {
                                false
                            },
                            onClick = {
                                if (!viewState.gameOver) {
                                    sendIntent(Move(it))
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}

private fun DrawScope.drawBorders(gridSize: Int, boardSize: Float) {
    val lineSpacing = boardSize / gridSize

    for (i in 1 until gridSize) {
        val offset = lineSpacing * i

        drawLine(
            color = Color.LightGray,
            start = Offset(offset, 0f),
            end = Offset(offset, boardSize),
            strokeWidth = 6f,
        )
        drawLine(
            color = Color.LightGray,
            start = Offset(0f, offset),
            end = Offset(boardSize, offset),
            strokeWidth = 6f,
        )
    }
}

@MultiPreview
@Composable
private fun BoardPreview() {
    val viewState = State(
        cells = listOf(
            Cell(0, EMPTY),
            Cell(1, CROSS),
            Cell(2, ZERO),
            Cell(3, ZERO),
            Cell(4, CROSS),
            Cell(5, EMPTY),
            Cell(6, CROSS),
            Cell(7, ZERO),
            Cell(8, EMPTY),
        ),
    )
    Board(viewState) { }
}