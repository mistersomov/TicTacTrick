package com.mistersomov.domain.use_case

import com.mistersomov.domain.entity.Cell
import com.mistersomov.domain.entity.GameStatus

interface GetGameStatusUseCase {
    operator fun invoke(cells: List<Cell>, isCrossMove: Boolean): GameStatus
}