package com.mistersomov.tictactrick.domain.use_case

import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.entity.board.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.Freezing
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.Tornado

class ApplyTrickyCardUseCaseImpl : ApplyTrickyCardUseCase {

    override operator fun invoke(cells: List<Cell>, card: TrickyCard): List<Cell> =
        when (card) {
            is Freezing -> applyFreezing(cells, card)
            is Tornado -> applyTornado(cells, card)
        }

    private fun applyFreezing(cells: List<Cell>, freezing: Freezing): List<Cell> {
        val cell: Cell? = cells
            .filter { !it.isLocked && it.type == EMPTY }
            .firstOrNull { it.id == freezing.sourceId }

        return cells.map {
            if (it.id == cell?.id) {
                it.copy(isLocked = true)
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

}