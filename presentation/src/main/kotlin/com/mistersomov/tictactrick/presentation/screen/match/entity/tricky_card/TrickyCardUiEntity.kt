package com.mistersomov.tictactrick.presentation.screen.match.entity.tricky_card

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.annotation.StringRes
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard

data class TrickyCardUiEntity(
    @DrawableRes
    val imageRes: Int,
    @StringRes
    val imageDescription: Int,
    @StringRes
    val description: Int,
    val card: TrickyCard,
    val isVisible: Boolean = true,
    val isEnabled: Boolean = true,
    @RawRes
    val soundEffectRes: Int,
)