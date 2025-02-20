package com.mistersomov.tictactrick.presentation.screen.match.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mistersomov.tictactrick.domain.entity.MatchStatus
import com.mistersomov.tictactrick.domain.entity.MatchStatus.Continue
import com.mistersomov.tictactrick.domain.entity.MatchStatus.Draw
import com.mistersomov.tictactrick.domain.entity.MatchStatus.Victory
import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.use_case.GetMatchStatusUseCase
import com.mistersomov.tictactrick.domain.use_case.GetMatchStatusUseCaseImpl
import com.mistersomov.tictactrick.domain.use_case.MoveUseCase
import com.mistersomov.tictactrick.domain.use_case.MoveUseCaseImpl
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Effect
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Effect.ShowDialog
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Intent
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Intent.Move
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Intent.Reset
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Intent.StartGame
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MatchViewModel(
    private val moveUseCase: MoveUseCase = MoveUseCaseImpl(),
    private val getMatchStatusUseCase: GetMatchStatusUseCase = GetMatchStatusUseCaseImpl(),
) : ViewModel() {

    private val _effect: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effect: Flow<Effect> = _effect

    private val _intent: MutableSharedFlow<Intent> = MutableSharedFlow()

    private val _viewState: MutableStateFlow<State> = MutableStateFlow(State())
    val viewState: StateFlow<State> by lazy { _viewState.asStateFlow() }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                MatchViewModel()
            }
        }
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
            is StartGame -> startGame()
            is Move -> move(intent.cellId)
            is Reset -> reset()
        }
    }

    private fun startGame() {
        val size = viewState.value.boardMode.value * viewState.value.boardMode.value
        setState { copy(cells = List(size) { Cell(id = it) }) }
    }

    private fun move(cellId: Int) {
        with(viewState.value) {
            val updatedCells: List<Cell> = moveUseCase(
                cells = cells,
                index = cellId,
                isCrossMove = isCrossMove,
            )
            val matchStatus: MatchStatus = getMatchStatusUseCase(
                cells = updatedCells,
                boardMode = boardMode,
                isCrossMove = isCrossMove,
            )

            when(matchStatus) {
                is Victory, Draw -> {
                    setState { copy(cells = updatedCells, matchStatus = matchStatus, gameOver = true) }
                    setEffect { ShowDialog }
                }
                is Continue -> setState { copy(cells = updatedCells, isCrossMove = !isCrossMove) }
            }
        }
    }

    private fun reset() {
        setState { State() }
        startGame()
    }
}