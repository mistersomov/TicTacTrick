package com.mistersomov.tictactrick.domain.use_case

import com.mistersomov.tictactrick.domain.entity.Cell
import com.mistersomov.tictactrick.domain.entity.FieldMode
import com.mistersomov.tictactrick.domain.entity.MatchStatus

interface GetMatchStatusUseCase {
    operator fun invoke(
        cells: List<Cell>,
        fieldMode: FieldMode,
        isCrossMove: Boolean,
    ): MatchStatus
}