package com.mistersomov.tictactrick.presentation.screen.match.view.tricky_card

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.mistersomov.tictactrick.presentation.screen.match.entity.tricky_card.TrickyCardUiEntity

@Composable
internal fun TrickyCardGroup(
    cards: List<TrickyCardUiEntity>,
    onCardClick: (TrickyCardUiEntity) -> Unit,
    onCardDragEnd: (TrickyCardUiEntity) -> Unit,
) {
    val density = LocalDensity.current

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        cards.forEachIndexed { index, entity ->
            AnimatedVisibility(
                visible = entity.isVisible,
                enter = slideInHorizontally {
                    with(density) {
                        if (index % 2 == 0) {
                            -200.dp.roundToPx()
                        } else {
                            200.dp.roundToPx()
                        }
                    }
                },
                exit = slideOutHorizontally {
                    with(density) {
                        if (index % 2 == 0) {
                            -200.dp.roundToPx()
                        } else {
                            200.dp.roundToPx()
                        }
                    }
                },
            ) {
                TrickyCard(
                    item = entity,
                    onClick = { onCardClick(entity) },
                    onDragEnd = { onCardDragEnd(entity) },
                )
            }
        }
    }
}

//@PreviewPhone
//@Composable
//private fun TrickyCardGroupPreview() {
//    TrickyCardGroup(
//        cards = listOf(TrickyCardUiEntity()),
//        onCardClick = {},
//    )
//}