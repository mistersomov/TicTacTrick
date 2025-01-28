package com.mistersomov.domain.use_case

import com.mistersomov.domain.model.Cell
import com.mistersomov.domain.model.CellType.CROSS
import com.mistersomov.domain.model.CellType.EMPTY
import com.mistersomov.domain.model.CellType.ZERO

internal class MoveUseCaseImpl : MoveUseCase {

    override fun invoke(
        cells: List<Cell>,
        index: Int,
        isCrossMove: Boolean,
    ): List<Cell> =
        cells
            .toMutableList()
            .also {
                val cell = cells[index]

                if (cell.type == EMPTY) {
                    it.removeAt(index)
                    it.add(index, cell.copy(type = if (isCrossMove) CROSS else ZERO))
                }
            }
}