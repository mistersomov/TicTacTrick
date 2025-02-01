package com.mistersomov.happyandhealth.domain.use_case

import com.mistersomov.happyandhealth.domain.entity.Cell
import com.mistersomov.happyandhealth.domain.entity.CellType.CROSS
import com.mistersomov.happyandhealth.domain.entity.CellType.EMPTY
import com.mistersomov.happyandhealth.domain.entity.CellType.ZERO

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