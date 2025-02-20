package com.mistersomov.tictactrick.domain.entity

sealed interface MatchStatus {
    data class Victory(
        val winner: CellType,
        val combination: List<Int>,
    ) : MatchStatus

    data object Draw : MatchStatus
    data object Continue : MatchStatus
}