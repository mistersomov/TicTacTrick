package com.mistersomov.tictactrick.presentation.screen.match.mapper

import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.entity.board.CellType.CROSS
import com.mistersomov.tictactrick.domain.entity.board.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.board.CellType.ZERO
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.SingleSelectable.Blaze
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.SingleSelectable.Freezing
import com.mistersomov.tictactrick.presentation.R
import com.mistersomov.tictactrick.presentation.screen.match.entity.board.CellUiEntity

internal fun Cell.toUi(trickyCard: TrickyCard?): CellUiEntity {
    val (imageRes: Int?, description: Int?) = when (type) {
        CROSS -> R.drawable.cross to R.string.cross
        ZERO -> R.drawable.zero to R.string.zero
        EMPTY -> null to null
    }
    val (lockedRes: Int?, lockedDescription: Int?) = when (trickyCard) {
        is Freezing -> R.drawable.ice to R.string.tricky_card_freezing
        is Blaze ->  R.drawable.lava to R.string.tricky_card_blaze
        else -> null to null
    }

    return CellUiEntity(
        id = id,
        imageRes = imageRes,
        imageDescription = description,
        isRevealed = type != EMPTY,
        trickyCard = trickyCard,
        lockedRes = lockedRes,
        lockedDescription = lockedDescription,
        remainingMoves = remainingMoves,
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
        remainingMoves = remainingMoves,
    )