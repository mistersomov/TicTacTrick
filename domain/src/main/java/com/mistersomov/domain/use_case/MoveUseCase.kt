package com.mistersomov.domain.use_case

import com.mistersomov.domain.entity.Cell

interface MoveUseCase {
    operator fun invoke(
        cells: List<Cell>,
        index: Int,
        isCrossMove: Boolean,
    ): List<Cell>
}