package com.mistersomov.tictactrick.presentation.screen.match.view.tricky_card

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.mistersomov.tictactrick.presentation.screen.match.entity.tricky_card.TrickyCardUiEntity

@Composable
internal fun TrickyCard(
    modifier: Modifier = Modifier,
    item: TrickyCardUiEntity,
    onClick: () -> Unit,
    onDragEnd: () -> Unit,
) {
    var offsetPositionX by remember { mutableFloatStateOf(0f) }
    var offsetPositionY by remember { mutableFloatStateOf(0f) }
    var cardScale by remember { mutableFloatStateOf(1f) }
    var isCardSelected by remember { mutableStateOf(false) }

    LaunchedEffect(isCardSelected) {
        cardScale = if (isCardSelected) 1.2f else 1f
    }

    Image(
        modifier = modifier
            .offset { IntOffset(offsetPositionX.toInt(), offsetPositionY.toInt()) }
            .pointerInput(Unit) {
                if (item.isEnabled) {
                    detectTapGestures(onTap = { onClick() })
                }
            }
            .pointerInput(Unit) {
                if (item.isEnabled) {
                    detectDragGestures(
                        onDragStart = { isCardSelected = true },
                        onDragEnd = {
                            isCardSelected = false
                            onDragEnd()
                        },
                        onDrag = { _, dragAmount ->
                            offsetPositionX += dragAmount.x
                            offsetPositionY += dragAmount.y
                        }
                    )
                }
            }
            .padding(16.dp)
            .size(150.dp)
            .scale(cardScale),
        painter = painterResource(item.imageRes),
        contentDescription = stringResource(item.imageDescription),
        colorFilter = if (!item.isEnabled) ColorFilter.colorMatrix(ColorMatrix().apply { setToSaturation(0f) }) else null,
    )
}