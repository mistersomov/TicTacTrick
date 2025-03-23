package com.mistersomov.tictactrick.presentation.screen.match.view.tricky_card

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.mistersomov.core.ui_kit.TicTacTrickTheme
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard
import com.mistersomov.tictactrick.presentation.R
import com.mistersomov.tictactrick.presentation.extension.PreviewPhone
import com.mistersomov.tictactrick.presentation.screen.match.entity.tricky_card.TrickyCardUiEntity
import kotlinx.coroutines.delay

@Composable
fun TrickyCardDetails(
    trickyCard: TrickyCardUiEntity,
    onDismiss: () -> Unit,
) {
    val density = LocalDensity.current

    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(50L)
        isVisible = true
    }

    Dialog(
        onDismissRequest = {
            isVisible = false
            onDismiss()
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInHorizontally { with(density) { -200.dp.roundToPx() } },
            ) {
                Image(
                    modifier = Modifier.size(200.dp),
                    painter = painterResource(trickyCard.imageRes),
                    contentDescription = stringResource(trickyCard.imageDescription),
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInHorizontally { with(density) { 220.dp.roundToPx() } }
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White,
                    text = stringResource(trickyCard.imageDescription),
                    fontSize = 20.sp
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            AnimatedVisibility(
                visible = isVisible,
                enter = slideInHorizontally { with(density) { 250.dp.roundToPx() } }
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color.White,
                    text = stringResource(trickyCard.description),
                )
            }
        }
    }
}

@PreviewPhone
@Composable
private fun TrickyCardDetailsPreview() {
    val blazeCard = TrickyCardUiEntity(
        imageRes = R.drawable.blaze,
        imageDescription = 0,
        description = R.string.tricky_card_blaze_description,
        card = TrickyCard.Selectable.SingleSelectable.Blaze(),
        soundEffectRes = R.raw.blaze,
    )
    TicTacTrickTheme {
        TrickyCardDetails(trickyCard = blazeCard) { }
    }
}