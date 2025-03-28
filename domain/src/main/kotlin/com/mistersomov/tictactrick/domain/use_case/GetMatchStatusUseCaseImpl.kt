package com.mistersomov.tictactrick.domain.use_case

import com.mistersomov.tictactrick.domain.entity.MatchStatus
import com.mistersomov.tictactrick.domain.entity.MatchStatus.Continue
import com.mistersomov.tictactrick.domain.entity.MatchStatus.Draw
import com.mistersomov.tictactrick.domain.entity.MatchStatus.Victory
import com.mistersomov.tictactrick.domain.entity.board.BoardMode
import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.entity.board.CellType.CROSS
import com.mistersomov.tictactrick.domain.entity.board.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.board.CellType.ZERO
import javax.inject.Inject

class GetMatchStatusUseCaseImpl @Inject constructor(): GetMatchStatusUseCase {

    override operator fun invoke(
        cells: List<Cell>,
        boardMode: BoardMode,
    ): MatchStatus {
        val combinations: List<List<Int>> = generateWinningCombinations(boardMode.value)
        val winningCombination: List<Int>? = combinations.firstOrNull { combination ->
            combination.all { index -> cells[index].type == CROSS }
                    || combination.all { index -> cells[index].type == ZERO }
        }

        return when {
            winningCombination != null -> Victory(cells[winningCombination[0]].type, winningCombination)
            cells.none { it.type == EMPTY && it.trickyCard == null } -> Draw
            else -> Continue
        }
    }

    private fun generateWinningCombinations(size: Int): List<List<Int>> {
        val combinations = mutableListOf<List<Int>>()

        for (i in 0 until size) {
            combinations.add(List(size) { horizontal -> i * size + horizontal })
            combinations.add(List(size) { vertical -> vertical * size + i })
        }
        combinations.add(List(size) { i -> i * (size + 1) })
        combinations.add(List(size) { i -> (i + 1) * (size - 1) })

        return combinations
    }
}