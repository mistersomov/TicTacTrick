package com.mistersomov.domain.use_case

import com.mistersomov.domain.entity.Cell
import com.mistersomov.domain.entity.CellType.CROSS
import com.mistersomov.domain.entity.CellType.EMPTY
import com.mistersomov.domain.entity.CellType.ZERO

class MoveUseCaseImpl : MoveUseCase {

    override fun invoke(
        cells: List<Cell>,
        index: Int,
        isCrossMove: Boolean,
    ): List<Cell> =
        cells
            .toMutableList()
            .also {
                val cell = it[index]

                if (cell.type == EMPTY) {
                    it.removeAt(index)
                    it.add(index, cell.copy(type = if (isCrossMove) CROSS else ZERO))
                }
            }
}