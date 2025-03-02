package com.mistersomov.tictactrick.domain.use_case

import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.entity.board.CellType.CROSS
import com.mistersomov.tictactrick.domain.entity.board.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.board.CellType.ZERO

class MoveUseCaseImpl : MoveUseCase {

    override operator fun invoke(
        cells: List<Cell>,
        index: Int,
        isCrossMove: Boolean,
    ): List<Cell> {
        if (cells[index].type != EMPTY || cells[index].isFrozen || cells[index].isBlazed) {
            return cells
        }

        val cellType = if (isCrossMove) CROSS else ZERO

        return cells.mapIndexed { i, cell ->
            if (i == index) {
                cell.copy(type = cellType)
            } else {
                cell
            }
        }
    }
}