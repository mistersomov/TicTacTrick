package com.mistersomov.tictactrick.presentation.screen.match

import com.mistersomov.tictactrick.domain.entity.MatchStatus
import com.mistersomov.tictactrick.domain.entity.MatchStatus.Continue
import com.mistersomov.tictactrick.domain.entity.board.BoardMode
import com.mistersomov.tictactrick.domain.entity.board.BoardMode.THREE
import com.mistersomov.tictactrick.domain.entity.board.Cell

interface MatchContract {

    sealed interface Intent {
        data object StartGame : Intent
        data class Move(val cellId: Int): Intent
        data object Reset : Intent
    }

    data class State(
        val cells: List<Cell> = emptyList(),
        val boardMode: BoardMode = THREE,
        val matchStatus: MatchStatus = Continue,
        val isCrossMove: Boolean = true,
        val gameOver: Boolean = false,
    )

    sealed interface Effect {
        data object ShowDialog : Effect
    }
}