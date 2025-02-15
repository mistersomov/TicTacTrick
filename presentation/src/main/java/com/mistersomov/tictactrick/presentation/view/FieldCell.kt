package com.mistersomov.tictactrick.presentation.view

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode.Reverse
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.coerceAtLeast
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import com.mistersomov.tictactrick.domain.entity.Cell
import com.mistersomov.tictactrick.domain.entity.CellType.CROSS
import com.mistersomov.tictactrick.domain.entity.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.CellType.ZERO
import com.mistersomov.tictactrick.presentation.R
import com.mistersomov.tictactrick.presentation.extension.MultiPreview

@Composable
fun FieldCell(
    item: Cell,
    gridSize: Int,
    isWinningCell: Boolean,
    onClick: (id: Int) -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    val infiniteTransition = rememberInfiniteTransition()
    val rotationVal by infiniteTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = Reverse,
        ),
    )
    val scaleVal = remember { Animatable(1f) }

    val cellSize = (120.dp / (gridSize / 3f)).coerceAtLeast(40.dp)
    val imageSize = 0.8 * cellSize
    var showImage by remember { mutableStateOf(false) }

    LaunchedEffect(isWinningCell) {
        if (isWinningCell) {
            scaleVal.animateTo(1.4f, tween(500))
            scaleVal.animateTo(1f, tween(500))
        }
    }

    Box(
        modifier = Modifier
            .size(cellSize)
            .border(BorderStroke(2.dp, LightGray), RoundedCornerShape(8.dp))
            .scale(scaleVal.value)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) {
                if (!showImage) {
                    showImage = true
                    onClick(item.id)
                }
            },
        contentAlignment = Alignment.Center,
    ) {
        if (showImage) {
            val (@DrawableRes imageRes: Int?, @StringRes description: Int?) = when (item.type) {
                CROSS -> R.drawable.cross to R.string.cross
                ZERO -> R.drawable.zero to R.string.zero
                EMPTY -> null to null
            }

            imageRes?.let { image ->
                Image(
                    modifier = Modifier
                        .size(imageSize)
                        .rotate(rotationVal),
                    painter = painterResource(image),
                    contentDescription = description?.let { stringResource(it) },
                )
            }
        }
    }
}

@MultiPreview
@Composable
private fun MatchCellPreview() {
    Column {
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            FieldCell(
                item = Cell(id = 0, type = CROSS),
                gridSize = 3,
                isWinningCell = true,
                onClick = {},
            )
            FieldCell(
                item = Cell(id = 0, type = ZERO),
                gridSize = 3,
                isWinningCell = false,
                onClick = {},
            )
        }
        Row(horizontalArrangement = Arrangement.SpaceBetween) {
            FieldCell(
                item = Cell(id = 0, type = CROSS),
                gridSize = 4,
                isWinningCell = true,
                onClick = {},
            )
            FieldCell(
                item = Cell(id = 0, type = ZERO),
                gridSize = 4,
                isWinningCell = false,
                onClick = {},
            )
        }
    }
}