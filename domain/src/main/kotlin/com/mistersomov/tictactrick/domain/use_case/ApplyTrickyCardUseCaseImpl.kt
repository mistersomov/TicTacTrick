package com.mistersomov.tictactrick.domain.use_case

import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.entity.board.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Global.Harmony
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.DualSelectable.Tornado
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.SingleSelectable
import javax.inject.Inject

class ApplyTrickyCardUseCaseImpl @Inject constructor(): ApplyTrickyCardUseCase {

    private companion object {
        const val REMAINING_MOVES = 3
    }

    override operator fun invoke(cells: List<Cell>, card: TrickyCard): List<Cell> =
        when (card) {
            is SingleSelectable-> applySingleSelectable(cells, card)
            is Tornado -> applyTornado(cells, card)
            is Harmony -> applyHarmony(cells)
        }

    private fun applySingleSelectable(cells: List<Cell>, card: SingleSelectable): List<Cell> {
        val cell: Cell? = cells
            .filter { it.trickyCard == null && it.type == EMPTY }
            .firstOrNull { it.id == card.sourceId }

        return cells.map {
            if (it.id == cell?.id) {
                it.copy(
                    trickyCard = card,
                    remainingMoves = REMAINING_MOVES,
                )
            } else {
                it.updateRemainingMoves()
            }
        }
    }

    private fun applyTornado(cells: List<Cell>, tornado: Tornado): List<Cell> {
        val sourceCell: Cell? = cells.firstOrNull { it.id == tornado.sourceId }
        val targetCell: Cell? = cells.firstOrNull { it.id == tornado.targetId }

        return cells.map {
            if (sourceCell != null  && targetCell != null) {
                when (it.id) {
                    sourceCell.id -> it.copy(type = targetCell.type)
                    targetCell.id -> it.copy(type = sourceCell.type)
                    else -> it.updateRemainingMoves()
                }
            } else  {
                it.updateRemainingMoves()
            }
        }
    }

    private fun applyHarmony(cells: List<Cell>): List<Cell> = cells.map {
        it.copy(
            trickyCard = null,
            remainingMoves = null,
        )
    }

    private fun Cell.updateRemainingMoves(): Cell {
        val remaining = remainingMoves?.minus(1)

        return copy(
            trickyCard = if (remaining == 0) null else trickyCard,
            remainingMoves = if (remaining == 0) null else remaining,
        )
    }

}