package com.mistersomov.happyandhealth.domain.use_case

import com.mistersomov.happyandhealth.domain.entity.Cell
import com.mistersomov.happyandhealth.domain.entity.CellType.CROSS
import com.mistersomov.happyandhealth.domain.entity.CellType.EMPTY
import com.mistersomov.happyandhealth.domain.entity.CellType.ZERO
import com.mistersomov.happyandhealth.domain.entity.GameStatus
import com.mistersomov.happyandhealth.domain.entity.GameStatus.Continue
import com.mistersomov.happyandhealth.domain.entity.GameStatus.Draw
import com.mistersomov.happyandhealth.domain.entity.GameStatus.Victory

class GetGameStatusUseCaseImpl : GetGameStatusUseCase {

    override operator fun invoke(cells: List<Cell>, isCrossMove: Boolean): GameStatus {
        val cellType = if (isCrossMove) CROSS else ZERO

        for (i in 0..6 step 3) {
            if (cells[i].type == cellType
                && cells[i + 1].type == cellType
                && cells[i + 2].type == cellType
            ) {
                return Victory(winner = cellType)
            }
        }
        for (i in 0..2 step 1) {
            if (cells[i].type == cellType
                && cells[i + 3].type == cellType
                && cells[i + 6].type == cellType
            ) {
                return Victory(winner = cellType)
            }
        }
        return when {
            cells[4].type == cellType
                    && (cells[0].type == cellType && cells[8].type == cellType)
                    || (cells[2].type == cellType && cells[6].type == cellType) -> Victory(winner = cellType)
            !cells.any { it.type == EMPTY } -> Draw
            else -> Continue
        }
    }
}