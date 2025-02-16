package com.mistersomov.tictactrick.domain.entity

import com.mistersomov.tictactrick.domain.entity.CellType.EMPTY

data class Cell(
    val id: Int,
    val type: CellType = EMPTY,
    val isRevealed: Boolean = false,
)