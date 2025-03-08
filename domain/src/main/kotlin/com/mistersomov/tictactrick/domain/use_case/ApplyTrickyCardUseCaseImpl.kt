package com.mistersomov.tictactrick.domain.use_case

import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.entity.board.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Global.Harmony
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.DualSelectable.Tornado
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.SingleSelectable
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.SingleSelectable.Blaze
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.SingleSelectable.Freezing
import javax.inject.Inject

class ApplyTrickyCardUseCaseImpl @Inject constructor(): ApplyTrickyCardUseCase {

    override operator fun invoke(cells: List<Cell>, card: TrickyCard): List<Cell> =
        when (card) {
            is SingleSelectable-> applySingleSelectable(cells, card)
            is Tornado -> applyTornado(cells, card)
            is Harmony -> applyHarmony(cells)
        }

    private fun applySingleSelectable(cells: List<Cell>, card: SingleSelectable): List<Cell> {
        val cell: Cell? = cells
            .filter { !it.isFrozen && !it.isBlazed && it.type == EMPTY }
            .firstOrNull { it.id == card.sourceId }

        return cells.map {
            if (it.id == cell?.id) {
                it.copy(
                    isFrozen = card is Freezing,
                    isBlazed = card is Blaze,
                )
            } else {
                it
            }
        }
    }

    private fun applyTornado(cells: List<Cell>, tornado: Tornado): List<Cell> {
        val sourceCell: Cell? = cells.firstOrNull { it.id == tornado.sourceId }
        val targetCell: Cell? = cells.firstOrNull { it.id == tornado.targetId }

        return if (sourceCell != null  && targetCell != null) {
            cells.map {
                when (it.id) {
                    sourceCell.id -> it.copy(type = targetCell.type)
                    targetCell.id -> it.copy(type = sourceCell.type)
                    else -> it
                }
            }
        } else  {
            cells
        }
    }

    private fun applyHarmony(cells: List<Cell>): List<Cell> = cells.map { it.copy(isFrozen = false) }

}