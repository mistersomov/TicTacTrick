package com.mistersomov.tictactrick.domain.entity

import com.mistersomov.tictactrick.domain.entity.board.CellType

sealed interface MatchStatus {
    data class Victory(
        val winner: CellType,
        val combination: List<Int>,
    ) : MatchStatus

    data object Draw : MatchStatus
    data object Continue : MatchStatus
}