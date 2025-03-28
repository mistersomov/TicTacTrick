package com.mistersomov.tictactrick.domain.entity.board

import com.mistersomov.tictactrick.domain.entity.board.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard

data class Cell(
    val id: Int,
    val type: CellType = EMPTY,
    val trickyCard: TrickyCard? = null,
    val remainingMoves: Int? = null,
)