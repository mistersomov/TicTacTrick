package com.mistersomov.tictactrick.presentation.view

import com.mistersomov.tictactrick.domain.entity.Cell
import com.mistersomov.tictactrick.domain.entity.FieldMode
import com.mistersomov.tictactrick.domain.entity.FieldMode.THREE
import com.mistersomov.tictactrick.domain.entity.GameStatus
import com.mistersomov.tictactrick.domain.entity.GameStatus.Continue

interface GameContract {

    sealed interface Intent {
        data object StartGame : Intent
        data class Move(val cellId: Int): Intent
        data object Reset : Intent
    }

    data class State(
        val cells: List<Cell> = emptyList(),
        val fieldMode: FieldMode = THREE,
        val gameStatus: GameStatus = Continue,
        val isCrossMove: Boolean = true,
        val gameOver: Boolean = false,
    )

    sealed interface Effect {
        data object ShowDialog : Effect
    }
}