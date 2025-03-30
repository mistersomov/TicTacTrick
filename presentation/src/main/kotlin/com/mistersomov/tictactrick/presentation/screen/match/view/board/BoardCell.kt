package com.mistersomov.tictactrick.presentation.screen.match.view.board

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import com.mistersomov.core.ui_kit.TicTacTrickTheme
import com.mistersomov.tictactrick.presentation.R
import com.mistersomov.tictactrick.presentation.extension.MultiPreview
import com.mistersomov.tictactrick.presentation.screen.match.entity.board.CellUiEntity
import kotlinx.coroutines.delay

@Composable
fun BoardCell(
    entity: CellUiEntity,
    cellSize: Dp,
    isWinningCell: Boolean,
    onClick: (cell: CellUiEntity) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    val scaleVal = remember { Animatable(1f) }
    val adjustedCellSize = cellSize.coerceAtLeast(24.dp)
    val imageSize = 0.8 * adjustedCellSize

    LaunchedEffect(isWinningCell) {
        if (isWinningCell) {
            delay(100)
            scaleVal.animateTo(
                targetValue = 1f,
                animationSpec = keyframes {
                    durationMillis = 1000
                    1.5f at 400 using LinearOutSlowInEasing
                },
            )
        }
    }
    LaunchedEffect(entity.isRevealed) {
        if (entity.isRevealed) {
            scaleVal.animateTo(
                targetValue = 1f,
                animationSpec = keyframes {
                    durationMillis = 100
                    1.5f at 70 using LinearOutSlowInEasing
                }
            )
        } else {
            scaleVal.snapTo(1f)
        }
    }

    Box(
        modifier = Modifier
            .size(adjustedCellSize)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = { onClick(entity) }
            ),
        contentAlignment = Alignment.Center,
    ) {
        when {
            entity.lockedRes != null && entity.remainingMoves != null -> {
                Image(
                    modifier = Modifier.size(adjustedCellSize),
                    painter = painterResource(entity.lockedRes),
                    contentDescription = entity.lockedDescription?.let { stringResource(it) },
                    contentScale = ContentScale.FillBounds
                )
                AnimatedContent(
                    targetState = entity.remainingMoves,
                    transitionSpec = {
                        slideInVertically { height -> -height } + fadeIn() togetherWith
                                slideOutVertically { height -> -height } + fadeOut()
                    },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = it.toString(),
                        fontSize = 36.sp,
                        color = Color.Green,
                    )
                }
            }
            entity.imageRes != null && entity.imageDescription != null -> {
                Image(
                    modifier = Modifier
                        .size(imageSize)
                        .scale(scaleVal.value),
                    painter = painterResource(entity.imageRes),
                    contentDescription = stringResource(entity.imageDescription),
                )
            }
        }
        Image(
            modifier = Modifier.size(adjustedCellSize),
            painter = painterResource(R.drawable.cell),
            contentDescription = stringResource(R.string.empty),
        )
    }
}

@MultiPreview
@Composable
private fun MatchCellPreview() {
    TicTacTrickTheme {
        Column {
            BoardCell(
                entity = CellUiEntity(
                    id = 0,
                    imageRes = null,
                    imageDescription = null,
                ),
                cellSize = 120.dp,
                isWinningCell = true,
                onClick = {},
            )
            BoardCell(
                entity = CellUiEntity(
                    id = 1,
                    imageRes = R.drawable.cross,
                    imageDescription = R.string.cross,
                ),
                cellSize = 120.dp,
                isWinningCell = true,
                onClick = {},
            )
            BoardCell(
                entity = CellUiEntity(
                    id = 2,
                    imageRes = R.drawable.zero,
                    imageDescription = R.string.zero,
                ),
                cellSize = 120.dp,
                isWinningCell = false,
                onClick = {},
            )
            BoardCell(
                entity = CellUiEntity(
                    id = 2,
                    lockedRes = R.drawable.ice,
                    lockedDescription = R.string.tricky_card_freezing,
                    imageRes = null,
                    imageDescription = null,
                    remainingMoves = 3,
                ),
                cellSize = 120.dp,
                isWinningCell = false,
                onClick = {},
            )
        }
    }
}