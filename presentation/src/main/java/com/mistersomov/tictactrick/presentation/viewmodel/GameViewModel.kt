package com.mistersomov.tictactrick.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.mistersomov.tictactrick.domain.entity.Cell
import com.mistersomov.tictactrick.domain.entity.GameStatus
import com.mistersomov.tictactrick.domain.entity.GameStatus.Continue
import com.mistersomov.tictactrick.domain.entity.GameStatus.Draw
import com.mistersomov.tictactrick.domain.entity.GameStatus.Victory
import com.mistersomov.tictactrick.domain.use_case.GetGameStatusUseCase
import com.mistersomov.tictactrick.domain.use_case.GetGameStatusUseCaseImpl
import com.mistersomov.tictactrick.domain.use_case.MoveUseCase
import com.mistersomov.tictactrick.domain.use_case.MoveUseCaseImpl
import com.mistersomov.tictactrick.presentation.view.GameContract.Effect
import com.mistersomov.tictactrick.presentation.view.GameContract.Effect.ShowDialog
import com.mistersomov.tictactrick.presentation.view.GameContract.Intent
import com.mistersomov.tictactrick.presentation.view.GameContract.Intent.Move
import com.mistersomov.tictactrick.presentation.view.GameContract.Intent.Reset
import com.mistersomov.tictactrick.presentation.view.GameContract.Intent.StartGame
import com.mistersomov.tictactrick.presentation.view.GameContract.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(
    private val moveUseCase: MoveUseCase = MoveUseCaseImpl(),
    private val getGameStatusUseCase: GetGameStatusUseCase = GetGameStatusUseCaseImpl(),
) : ViewModel() {

    private val _effect: MutableSharedFlow<Effect> = MutableSharedFlow()
    val effect: Flow<Effect> = _effect

    private val _intent: MutableSharedFlow<Intent> = MutableSharedFlow()

    private val _viewState: MutableStateFlow<State> = MutableStateFlow(State())
    val viewState: StateFlow<State> by lazy { _viewState.asStateFlow() }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                GameViewModel()
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
        val size = viewState.value.fieldMode.value * viewState.value.fieldMode.value
        setState { copy(cells = List(size) { Cell(id = it) }) }
    }

    private fun move(cellId: Int) {
        with(viewState.value) {
            val updatedCells: List<Cell> = moveUseCase(
                cells = cells,
                index = cellId,
                isCrossMove = isCrossMove,
            )
            val gameStatus: GameStatus = getGameStatusUseCase(
                cells = updatedCells,
                fieldMode = fieldMode,
                isCrossMove = isCrossMove,
            )

            when(gameStatus) {
                is Victory, Draw -> {
                    setState { copy(cells = updatedCells, gameStatus = gameStatus, gameOver = true) }
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