package com.mistersomov.tictactrick.domain.use_case

import com.mistersomov.tictactrick.domain.entity.Cell
import com.mistersomov.tictactrick.domain.entity.CellType.CROSS
import com.mistersomov.tictactrick.domain.entity.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.CellType.ZERO

class MoveUseCaseImpl : MoveUseCase {

    override operator fun invoke(
        cells: List<Cell>,
        index: Int,
        isCrossMove: Boolean,
    ): List<Cell> {
        if (cells[index].type != EMPTY) return cells

        val cellType = if (isCrossMove) CROSS else  ZERO

        return cells.mapIndexed { i, cell ->
            if (i == index) {
                cell.copy(type = cellType, isRevealed = true)
            } else {
                cell
            }
        }
    }
}