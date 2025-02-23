package com.mistersomov.tictactrick.presentation.screen.match

import com.mistersomov.tictactrick.domain.entity.MatchStatus
import com.mistersomov.tictactrick.domain.entity.MatchStatus.Continue
import com.mistersomov.tictactrick.domain.entity.board.BoardMode
import com.mistersomov.tictactrick.domain.entity.board.BoardMode.FOUR
import com.mistersomov.tictactrick.presentation.screen.match.entity.board.CellUiEntity
import com.mistersomov.tictactrick.presentation.screen.match.entity.tricky_card.TrickyCardUiEntity

interface MatchContract {

    sealed interface Intent {
        data object StartGame : Intent
        data class Move(val cell: CellUiEntity): Intent
        data object Restart : Intent
        data class ActivateTrickyCard(val card: TrickyCardUiEntity) : Intent
    }

    data class State(
        val cells: List<CellUiEntity> = emptyList(),
        val selectedCells: List<CellUiEntity> = emptyList(),
        val boardMode: BoardMode = FOUR,
        val matchStatus: MatchStatus = Continue,
        val isCrossMove: Boolean = true,
        val gameOver: Boolean = false,
        val trickyCards: List<TrickyCardUiEntity> = emptyList(),
        val trickyCardSelected: TrickyCardUiEntity? = null,
    )

    sealed interface Effect {
        data object ShowDialog : Effect
    }
}