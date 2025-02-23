package com.mistersomov.tictactrick.presentation.screen.match.mutator

import com.mistersomov.tictactrick.domain.entity.MatchStatus
import com.mistersomov.tictactrick.domain.entity.board.BoardMode
import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard
import com.mistersomov.tictactrick.presentation.screen.match.entity.tricky_card.TrickyCardUiEntity

sealed interface MatchMutatorEvent {
    data class StartMatch(
        val mode: BoardMode,
        val trickyCards: List<TrickyCard>,
    ) : MatchMutatorEvent
    data class Move(
        val updatedCells: List<Cell>,
        val matchStatus: MatchStatus,
    ) : MatchMutatorEvent
    data class ActivateTrickyCard(val trickyCard: TrickyCardUiEntity) : MatchMutatorEvent
    data class ApplyTrickyCard(
        val updatedCells: List<Cell>,
        val matchStatus: MatchStatus,
    ) : MatchMutatorEvent
}