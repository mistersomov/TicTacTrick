package com.mistersomov.tictactrick.domain.use_case

import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.entity.board.CellType.CROSS
import com.mistersomov.tictactrick.domain.entity.board.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.board.CellType.ZERO
import javax.inject.Inject

class MoveUseCaseImpl @Inject constructor(): MoveUseCase {

    override operator fun invoke(
        cells: List<Cell>,
        index: Int,
        isCrossMove: Boolean,
    ): List<Cell> {
        if (cells[index].type != EMPTY || cells[index].trickyCard != null) {
            return cells
        }

        val cellType = if (isCrossMove) CROSS else ZERO

        return cells.mapIndexed { i, cell ->
            val remaining = cell.remainingMoves?.minus(1)

            cell.copy(
                type = if (i == index) cellType else cell.type,
                remainingMoves = if (remaining == 0) null else remaining,
                trickyCard = if (remaining == 0) null else cell.trickyCard,
            )
        }
    }
}