package com.mistersomov.tictactrick.presentation.screen.match.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mistersomov.tictactrick.domain.entity.MatchStatus.Continue
import com.mistersomov.tictactrick.domain.entity.board.BoardMode
import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Global
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.DualSelectable
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.DualSelectable.Tornado
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.SingleSelectable
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.SingleSelectable.Blaze
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.SingleSelectable.Freezing
import com.mistersomov.tictactrick.domain.use_case.ApplyTrickyCardUseCase
import com.mistersomov.tictactrick.domain.use_case.GetMatchStatusUseCase
import com.mistersomov.tictactrick.domain.use_case.GetRandomTrickyCardUseCase
import com.mistersomov.tictactrick.domain.use_case.MoveUseCase
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Effect
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Effect.ShowDialog
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Intent
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Intent.ActivateTrickyCard
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Intent.Move
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Intent.Restart
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Intent.StartGame
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.State
import com.mistersomov.tictactrick.presentation.screen.match.entity.board.CellUiEntity
import com.mistersomov.tictactrick.presentation.screen.match.entity.tricky_card.TrickyCardUiEntity
import com.mistersomov.tictactrick.presentation.screen.match.mapper.toDomain
import com.mistersomov.tictactrick.presentation.screen.match.mutator.MatchMutatorEvent
import com.mistersomov.tictactrick.presentation.screen.match.mutator.MatchStateMutator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(
    private val applyTrickyCardUseCase: ApplyTrickyCardUseCase,
    private val getMatchStatusUseCase: GetMatchStatusUseCase,
    private val getRandomTrickyCardUseCase: GetRandomTrickyCardUseCase,
    private val moveUseCase: MoveUseCase,
    private val mutator: MatchStateMutator,
) : ViewModel() {

    private val _effect: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effect: Flow<Effect> = _effect

    private val _intent: MutableSharedFlow<Intent> = MutableSharedFlow()

    private val _viewState: MutableStateFlow<State> = MutableStateFlow(State())
    val viewState: StateFlow<State> by lazy { _viewState.asStateFlow() }

    private companion object {
         const val MULTIPLE_SELECT = 2
    }

    init {
        viewModelScope.launch {
            _intent.collect(::handleIntent)
        }
    }

    fun sendIntent(intent: Intent) {
        viewModelScope.launch {
            _intent.emit(intent)
        }
    }

    private fun setState(reducer: State.() -> State) {
        _viewState.update(reducer)
    }

    private fun setEffect(builder: () -> Effect) {
        viewModelScope.launch {
            _effect.emit(builder())
        }
    }

    private fun handleIntent(intent: Intent) {
        when (intent) {
            is StartGame, Restart -> startGame()
            is Move -> move(intent.cell)
            is ActivateTrickyCard -> activateTrickyCard(intent.card)
        }
    }

    private fun startGame() {
        val trickyCards = getRandomTrickyCardUseCase()
        setState {
            mutator.mutate(
                currentState = viewState.value,
                event = MatchMutatorEvent.StartMatch(
                    mode = BoardMode.FOUR, // TODO Прокинуть через аргумиенты
                    trickyCards = trickyCards,
                ),
            )
        }
    }

    private fun move(cell: CellUiEntity) {
        with(viewState.value) {
            when {
                trickyCardSelected?.card is Selectable -> selectCell(cell)
                !cell.isRevealed && !gameOver -> {
                    val updatedCells: List<Cell> = moveUseCase(
                        cells = cells.map { it.toDomain() },
                        index = cell.id,
                        isCrossMove = isCrossMove,
                    )
                    val matchStatus = getMatchStatusUseCase(
                        cells = updatedCells,
                        boardMode = boardMode,
                    )

                    setState {
                        mutator.mutate(
                            currentState = this,
                            event = MatchMutatorEvent.Move(
                                updatedCells = updatedCells,
                                matchStatus = matchStatus,
                            ),
                        )
                    }
                    if (matchStatus != Continue) {
                        setEffect { ShowDialog }
                    }
                }
            }
        }
    }

    private fun applyGlobalTrickyCard() {
        with(viewState.value) {
            trickyCardSelected?.card?.let { trickyCard ->
                val updatedCells = applyTrickyCardUseCase(
                    cells = cells.map { it.toDomain() },
                    card = trickyCard,
                )

                setState {
                    mutator.mutate(
                        currentState = this,
                        event = MatchMutatorEvent.ApplyTrickyCard(
                            updatedCells = updatedCells,
                            matchStatus = Continue,
                        )
                    )
                }
            }
        }
    }

    private fun activateTrickyCard(card: TrickyCardUiEntity) {
        setState {
            mutator.mutate(
                currentState = viewState.value,
                event = MatchMutatorEvent.ActivateTrickyCard(trickyCard = card),
            )
        }

        if (card.card is Global) {
            applyGlobalTrickyCard()
        }
    }

    private fun selectCell(cell: CellUiEntity) {
        viewState.value.trickyCardSelected?.let { trickyCard ->
            if (trickyCard.card is Selectable) {
                when (trickyCard.card) {
                    is SingleSelectable -> selectSingleCell(cell, trickyCard.card)
                    is Tornado -> selectMultipleCell(cell, trickyCard.card)
                }
            }
        }
    }

    private fun selectSingleCell(cell: CellUiEntity, card: SingleSelectable) {
        if (cell.isRevealed) return

        with(viewState.value) {
            val updatedCells = applyTrickyCardUseCase(
                cells = cells.map { it.toDomain() },
                card = when (card) {
                    is Freezing -> card.copy(sourceId = cell.id)
                    is Blaze -> card.copy(sourceId = cell.id)
                },
            )
            val matchStatus = getMatchStatusUseCase(
                cells = updatedCells,
                boardMode = boardMode,
            )

            setState {
                mutator.mutate(
                    currentState = this,
                    event = MatchMutatorEvent.ApplyTrickyCard(
                        updatedCells = updatedCells,
                        matchStatus = matchStatus,
                    )
                )
            }
        }
    }

    private fun selectMultipleCell(cell: CellUiEntity, card: DualSelectable) {
        if (!cell.isRevealed || cell.imageRes in viewState.value.selectedCells.map { it.imageRes }) {
            return
        }

        with(viewState.value) {
            val updatedSelectedCells = selectedCells + cell.copy(isSelected = true)
            if (updatedSelectedCells.size < MULTIPLE_SELECT) {
                setState { copy(selectedCells = updatedSelectedCells) }
                return
            }

            val updatedCells = applyTrickyCardUseCase(
                cells = cells.map { it.toDomain() },
                card = (card as Tornado).copy(
                    sourceId = updatedSelectedCells[0].id,
                    targetId = updatedSelectedCells[1].id,
                ),
            )
            val matchStatus = getMatchStatusUseCase(
                cells = updatedCells,
                boardMode = boardMode,
            )

            setState {
                mutator.mutate(
                    currentState = this,
                    event = MatchMutatorEvent.ApplyTrickyCard(updatedCells, matchStatus),
                )
            }

            if (matchStatus != Continue) {
                setEffect { ShowDialog }
            }
        }
    }

}