package com.mistersomov.tictactrick.domain.use_case

import com.mistersomov.tictactrick.domain.entity.Cell
import com.mistersomov.tictactrick.domain.entity.GameStatus

interface GetGameStatusUseCase {
    operator fun invoke(cells: List<Cell>, isCrossMove: Boolean): GameStatus
}