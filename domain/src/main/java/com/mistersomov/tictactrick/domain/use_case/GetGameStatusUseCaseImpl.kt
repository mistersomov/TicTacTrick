package com.mistersomov.tictactrick.domain.use_case

import androidx.annotation.VisibleForTesting
import com.mistersomov.tictactrick.domain.entity.Cell
import com.mistersomov.tictactrick.domain.entity.CellType.CROSS
import com.mistersomov.tictactrick.domain.entity.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.CellType.ZERO
import com.mistersomov.tictactrick.domain.entity.FieldMode
import com.mistersomov.tictactrick.domain.entity.GameStatus
import com.mistersomov.tictactrick.domain.entity.GameStatus.Continue
import com.mistersomov.tictactrick.domain.entity.GameStatus.Draw
import com.mistersomov.tictactrick.domain.entity.GameStatus.Victory

class GetGameStatusUseCaseImpl : GetGameStatusUseCase {

    override operator fun invoke(
        cells: List<Cell>,
        fieldMode: FieldMode,
        isCrossMove: Boolean,
    ): GameStatus {
        val cellType = if (isCrossMove) CROSS else ZERO
        val combinations = generateWinningCombinations(fieldMode.value)
        val isWinning = combinations.any { combination ->
            combination.all { index -> cells[index].type == cellType }
        }

        return when {
            isWinning -> Victory(cellType)
            cells.none { it.type == EMPTY } -> Draw
            else -> Continue
        }
    }

    @VisibleForTesting
    fun generateWinningCombinations(size: Int): List<List<Int>> {
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