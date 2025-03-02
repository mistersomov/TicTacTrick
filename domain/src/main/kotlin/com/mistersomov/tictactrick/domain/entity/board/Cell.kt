package com.mistersomov.tictactrick.domain.entity.board

import com.mistersomov.tictactrick.domain.entity.board.CellType.EMPTY

data class Cell(
    val id: Int,
    val type: CellType = EMPTY,
    val isRevealed: Boolean = false,
    val isFrozen: Boolean = false,
    val isBlazed: Boolean = false,
)