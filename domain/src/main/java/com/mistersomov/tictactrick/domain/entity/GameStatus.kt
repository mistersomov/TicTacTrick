package com.mistersomov.tictactrick.domain.entity

sealed interface GameStatus {
    data class Victory(val winner: CellType) : GameStatus
    data object Draw : GameStatus
    data object Continue : GameStatus
}