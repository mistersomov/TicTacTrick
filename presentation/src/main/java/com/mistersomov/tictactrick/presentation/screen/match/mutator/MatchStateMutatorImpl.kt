package com.mistersomov.tictactrick.presentation.screen.match.mutator

import com.mistersomov.tictactrick.domain.entity.MatchStatus
import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.State
import com.mistersomov.tictactrick.presentation.screen.match.entity.tricky_card.toUi
import com.mistersomov.tictactrick.presentation.screen.match.mapper.toUi
import com.mistersomov.tictactrick.presentation.screen.match.mutator.MatchMutatorEvent.ActivateTrickyCard
import com.mistersomov.tictactrick.presentation.screen.match.mutator.MatchMutatorEvent.ApplyTrickyCard
import com.mistersomov.tictactrick.presentation.screen.match.mutator.MatchMutatorEvent.Move
import com.mistersomov.tictactrick.presentation.screen.match.mutator.MatchMutatorEvent.StartMatch
import javax.inject.Inject

class MatchStateMutatorImpl @Inject constructor(): MatchStateMutator {

    override fun mutate(currentState: State, event: MatchMutatorEvent): State =
        when (event) {
            is StartMatch -> {
                val size = event.mode.value * event.mode.value
                State(
                    cells = List(size) { Cell(id = it).toUi(trickyCard = null) },
                    boardMode = event.mode,
                    trickyCards = event.trickyCards.map { it.toUi() },
                )
            }
            is Move -> {
                currentState.updateGameState(
                    updatedCells = event.updatedCells,
                    matchStatus = event.matchStatus,
                )
            }
            is ActivateTrickyCard -> {
                currentState.copy(
                    trickyCards = currentState.trickyCards.map {
                        if (it.card == event.trickyCard.card) {
                            it.copy(isVisible = false)
                        } else {
                            it.copy(isEnabled = false)
                        }
                    },
                    trickyCardSelected = event.trickyCard,
                )
            }
            is ApplyTrickyCard -> {
                currentState.updateGameState(
                    updatedCells = event.updatedCells,
                    matchStatus = event.matchStatus,
                ).copy(
                    selectedCells = emptyList(),
                    trickyCards = currentState.trickyCards.map { it.copy(isEnabled = true) },
                    trickyCardSelected = null,
                )
            }
        }

    private fun State.updateGameState(
        updatedCells: List<Cell>,
        matchStatus: MatchStatus,
    ) = copy(
        cells = updatedCells.map { cell -> cell.toUi(trickyCard = cell.trickyCard) },
        matchStatus = matchStatus,
        isCrossMove = !isCrossMove,
    )
}