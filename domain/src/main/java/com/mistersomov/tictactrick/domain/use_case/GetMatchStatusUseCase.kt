package com.mistersomov.tictactrick.domain.use_case

import com.mistersomov.tictactrick.domain.entity.MatchStatus
import com.mistersomov.tictactrick.domain.entity.board.BoardMode
import com.mistersomov.tictactrick.domain.entity.board.Cell

interface GetMatchStatusUseCase {
    operator fun invoke(
        cells: List<Cell>,
        boardMode: BoardMode,
    ): MatchStatus
}