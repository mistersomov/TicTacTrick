package com.mistersomov.tictactrick.domain.algorithm

import com.mistersomov.tictactrick.domain.entity.ai.AiMoveEntity
import com.mistersomov.tictactrick.domain.entity.board.BoardMode
import com.mistersomov.tictactrick.domain.entity.board.Cell

interface Algorithm {
    fun getBestMove(
        cells: List<Cell>,
        boardMode: BoardMode,
        isCrossMove: Boolean,
    ): AiMoveEntity
}