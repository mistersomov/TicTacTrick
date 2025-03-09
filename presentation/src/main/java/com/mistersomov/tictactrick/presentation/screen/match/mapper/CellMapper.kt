package com.mistersomov.tictactrick.presentation.screen.match.mapper

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.entity.board.CellType.CROSS
import com.mistersomov.tictactrick.domain.entity.board.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.board.CellType.ZERO
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard
import com.mistersomov.tictactrick.presentation.R
import com.mistersomov.tictactrick.presentation.screen.match.entity.board.CellUiEntity

internal fun Cell.toUi(trickyCard: TrickyCard?): CellUiEntity {
    val (@DrawableRes imageRes: Int?, @StringRes description: Int?) = when (type) {
        CROSS -> R.drawable.cross to R.string.cross
        ZERO -> R.drawable.zero to R.string.zero
        EMPTY -> null to null
    }
    val (card: TrickyCard?, @DrawableRes lockedRes) = when (trickyCard) {
        is TrickyCard.Selectable.SingleSelectable.Freezing -> trickyCard to R.drawable.ice
        is TrickyCard.Selectable.SingleSelectable.Blaze -> trickyCard to R.drawable.lava
        else -> null to null
    }

    return CellUiEntity(
        id = id,
        imageRes = imageRes,
        imageDescription = description,
        isRevealed = type != EMPTY,
        trickyCard = card,
        lockedRes = lockedRes,
    )
}

internal fun CellUiEntity.toDomain(): Cell =
    Cell(
        id = id,
        type = when (imageRes) {
            R.drawable.cross -> CROSS
            R.drawable.zero -> ZERO
            else -> EMPTY
        },
        trickyCard = trickyCard,
    )