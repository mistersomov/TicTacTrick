package com.mistersomov.domain.use_case

import com.mistersomov.domain.model.Cell

interface MoveUseCase {
    operator fun invoke(
        cells: List<Cell>,
        index: Int,
        isCrossMove: Boolean,
    ): List<Cell>
}