package com.mistersomov.tictactrick.presentation.screen.match.entity.board

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard

data class CellUiEntity(
    val id: Int,
    @DrawableRes
    val imageRes: Int? = null,
    @StringRes
    val imageDescription: Int? = null,
    val isWinningCell: Boolean = false,
    val isRevealed: Boolean = false,
    val isSelected: Boolean = false,
    val trickyCard: TrickyCard? = null,
    @DrawableRes
    val lockedRes: Int? = null,
)