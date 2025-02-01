package com.mistersomov.happyandhealth.domain.use_case

import com.mistersomov.happyandhealth.domain.entity.Cell

interface MoveUseCase {
    operator fun invoke(
        cells: List<Cell>,
        index: Int,
        isCrossMove: Boolean,
    ): List<Cell>
}