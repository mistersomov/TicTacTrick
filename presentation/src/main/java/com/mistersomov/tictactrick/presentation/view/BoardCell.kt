package com.mistersomov.tictactrick.presentation.view

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.mistersomov.tictactrick.domain.entity.Cell
import com.mistersomov.tictactrick.domain.entity.CellType.CROSS
import com.mistersomov.tictactrick.domain.entity.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.CellType.ZERO
import com.mistersomov.tictactrick.presentation.R
import com.mistersomov.tictactrick.presentation.extension.MultiPreview
import kotlinx.coroutines.delay

@Composable
fun BoardCell(
    item: Cell,
    cellSize: Dp,
    isWinningCell: Boolean,
    onClick: (id: Int) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    val scaleVal = remember { Animatable(1.5f) }

    val adjustedCellSize = cellSize.coerceAtLeast(24.dp)
    val imageSize = 0.85 * adjustedCellSize

    LaunchedEffect(isWinningCell) {
        if (isWinningCell) {
            delay(300)
            scaleVal.animateTo(
                targetValue = 1f,
                animationSpec = keyframes {
                    durationMillis = 1000
                    1.5f at 400 using LinearOutSlowInEasing
                },
            )
        }
    }
    LaunchedEffect(item.isRevealed) {
        if (item.isRevealed) {
            scaleVal.animateTo(
                targetValue = 1f,
                animationSpec = tween(50)
            )
        } else {
            scaleVal.snapTo(1.5f)
        }
    }

    Box(
        modifier = Modifier
            .size(adjustedCellSize)
            .scale(scaleVal.value)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) {
                if (!item.isRevealed) {
                    onClick(item.id)
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        val (@DrawableRes imageRes: Int?, @StringRes description: Int?) = when (item.type) {
            CROSS -> R.drawable.cross to R.string.cross
            ZERO -> R.drawable.zero to R.string.zero
            EMPTY -> null to null
        }

        imageRes?.let { image ->
            Image(
                modifier = Modifier.size(imageSize),
                painter = painterResource(image),
                contentDescription = description?.let { stringResource(it) },
            )
        }
    }
}

@MultiPreview
@Composable
private fun MatchCellPreview() {
    Column {
        BoardCell(
            item = Cell(id = 0, type = CROSS),
            cellSize = 120.dp,
            isWinningCell = true,
            onClick = {},
        )
        BoardCell(
            item = Cell(id = 0, type = ZERO),
            cellSize = 120.dp,
            isWinningCell = false,
            onClick = {},
        )
    }
}