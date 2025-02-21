package com.mistersomov.tictactrick.domain.use_case

import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.entity.board.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Freezing
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Tornado

class ApplyTrickyCardUseCaseImpl : ApplyTrickyCardUseCase {

    override operator fun invoke(cells: List<Cell>, card: TrickyCard): List<Cell> =
        when (card) {
            is Freezing -> applyFreezing(cells, card)
            is Tornado -> applyTornado(cells)
        }

    private fun applyFreezing(cells: List<Cell>, freezing: Freezing): List<Cell> {
        val cell: Cell? = cells
            .filter { !it.isLocked && it.type == EMPTY }
            .firstOrNull { it.id == freezing.cellId }

        return cells.map {
            if (it.id == cell?.id) {
                it.copy(isLocked = true)
            } else {
                it
            }
        }
    }

    private fun applyTornado(cells: List<Cell>): List<Cell> {
        val shuffledTypes =
            cells
                .map { it.type }
                .shuffled()

        return cells.map { cell ->
            val type = shuffledTypes[cell.id]
            cell.copy(
                type = type,
                isRevealed = type != EMPTY,
            )
        }
    }

}