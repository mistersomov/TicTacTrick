package com.mistersomov.tictactrick.domain.use_case

import com.mistersomov.tictactrick.domain.entity.Cell

interface MoveUseCase {
    operator fun invoke(
        cells: List<Cell>,
        index: Int,
        isCrossMove: Boolean,
    ): List<Cell>
}