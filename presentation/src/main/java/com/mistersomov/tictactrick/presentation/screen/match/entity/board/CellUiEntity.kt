package com.mistersomov.tictactrick.presentation.screen.match.entity.board

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.entity.board.CellType.CROSS
import com.mistersomov.tictactrick.domain.entity.board.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.board.CellType.ZERO
import com.mistersomov.tictactrick.presentation.R

data class CellUiEntity(
    val id: Int,
    @DrawableRes
    val imageRes: Int? = null,
    @StringRes
    val imageDescription: Int? = null,
    val isWinningCell: Boolean = false,
    val isRevealed: Boolean = false,
    val isSelected: Boolean = false,
    val isFrozen: Boolean = false,
    val isBlazed: Boolean = false,
)

fun Cell.toUi(): CellUiEntity {
    val (@DrawableRes imageRes: Int?, @StringRes description: Int?) = when (type) {
        CROSS -> R.drawable.cross to R.string.cross
        ZERO -> R.drawable.zero to R.string.zero
        EMPTY -> null to null
    }

    return CellUiEntity(
        id = id,
        imageRes = imageRes,
        imageDescription = description,
        isRevealed = type != EMPTY,
        isFrozen = isFrozen,
        isBlazed = isBlazed,
    )
}

fun CellUiEntity.toDomain(): Cell =
    Cell(
        id = id,
        type = when (imageRes) {
            R.drawable.cross -> CROSS
            R.drawable.zero -> ZERO
            else -> EMPTY
        },
        isRevealed = isRevealed,
        isFrozen = isFrozen,
        isBlazed = isBlazed,
    )