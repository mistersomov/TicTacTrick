package com.mistersomov.tictactrick.presentation.screen.match.entity.ai

import com.mistersomov.tictactrick.domain.algorithm.Algorithm
import com.mistersomov.tictactrick.domain.entity.board.BoardMode
import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.use_case.MoveUseCase
import javax.inject.Inject

class AiPlayer @Inject constructor(
    private val algorithm: Algorithm,
    private val moveUseCase: MoveUseCase,
) {

    fun move(
        cells: List<Cell>,
        boardMode: BoardMode,
        isCrossMove: Boolean,
    ): List<Cell> {
        val result = algorithm.getBestMove(
            cells = cells,
            boardMode = boardMode,
            isCrossMove = isCrossMove,
        )

        return moveUseCase(
            cells = cells,
            index = result.index,
            isCrossMove = isCrossMove,
        )
    }
}