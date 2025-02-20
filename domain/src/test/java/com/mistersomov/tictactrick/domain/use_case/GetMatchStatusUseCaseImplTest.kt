package com.mistersomov.tictactrick.domain.use_case

import com.mistersomov.tictactrick.domain.entity.Cell
import com.mistersomov.tictactrick.domain.entity.CellType.CROSS
import com.mistersomov.tictactrick.domain.entity.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.CellType.ZERO
import com.mistersomov.tictactrick.domain.entity.FieldMode
import com.mistersomov.tictactrick.domain.entity.MatchStatus
import com.mistersomov.tictactrick.domain.entity.MatchStatus.Continue
import com.mistersomov.tictactrick.domain.entity.MatchStatus.Draw
import com.mistersomov.tictactrick.domain.entity.MatchStatus.Victory
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import kotlin.test.assertEquals

internal class GetMatchStatusUseCaseImplTest {
    private val getMatchStatusUseCase = GetMatchStatusUseCaseImpl()

    enum class Status(
        val cells: List<Cell>,
        val isCrossMove: Boolean,
        val expected: MatchStatus,
    ) {
        WIN_BY_FIRST_ROW_CROSS(
            cells = listOf(
                Cell(id = 0, type = CROSS),
                Cell(id = 1, type = CROSS),
                Cell(id = 2, type = CROSS),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = EMPTY),
                Cell(id = 5, type = EMPTY),
                Cell(id = 6, type = EMPTY),
                Cell(id = 7, type = EMPTY),
                Cell(id = 8, type = EMPTY),
            ),
            isCrossMove = true,
            expected = Victory(
                winner = CROSS,
                combination = listOf(0, 1, 2),
            ),
        ),
        WIN_BY_SECOND_ROW_CROSS(
            cells = listOf(
                Cell(id = 0, type = EMPTY),
                Cell(id = 1, type = EMPTY),
                Cell(id = 2, type = EMPTY),
                Cell(id = 3, type = CROSS),
                Cell(id = 4, type = CROSS),
                Cell(id = 5, type = CROSS),
                Cell(id = 6, type = EMPTY),
                Cell(id = 7, type = EMPTY),
                Cell(id = 8, type = EMPTY),
            ),
            isCrossMove = true,
            expected = Victory(
                winner = CROSS,
                combination = listOf(3, 4, 5),
            ),
        ),
        WIN_BY_THIRD_ROW_CROSS(
            cells = listOf(
                Cell(id = 0, type = EMPTY),
                Cell(id = 1, type = EMPTY),
                Cell(id = 2, type = EMPTY),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = EMPTY),
                Cell(id = 5, type = EMPTY),
                Cell(id = 6, type = CROSS),
                Cell(id = 7, type = CROSS),
                Cell(id = 8, type = CROSS),
            ),
            isCrossMove = true,
            expected = Victory(
                winner = CROSS,
                combination = listOf(6, 7, 8),
            ),
        ),
        WIN_BY_FIRST_COLUMN_CROSS(
            cells = listOf(
                Cell(id = 0, type = CROSS),
                Cell(id = 1, type = EMPTY),
                Cell(id = 2, type = EMPTY),
                Cell(id = 3, type = CROSS),
                Cell(id = 4, type = EMPTY),
                Cell(id = 5, type = EMPTY),
                Cell(id = 6, type = CROSS),
                Cell(id = 7, type = EMPTY),
                Cell(id = 8, type = EMPTY),
            ),
            isCrossMove = true,
            expected = Victory(
                winner = CROSS,
                combination = listOf(0, 3, 6),
            ),
        ),
        WIN_BY_SECOND_COLUMN_CROSS(
            cells = listOf(
                Cell(id = 0, type = EMPTY),
                Cell(id = 1, type = CROSS),
                Cell(id = 2, type = EMPTY),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = CROSS),
                Cell(id = 5, type = EMPTY),
                Cell(id = 6, type = EMPTY),
                Cell(id = 7, type = CROSS),
                Cell(id = 8, type = EMPTY),
            ),
            isCrossMove = true,
            expected = Victory(
                winner = CROSS,
                combination = listOf(1, 4, 7),
            ),
        ),
        WIN_BY_THIRD_COLUMN_CROSS(
            cells = listOf(
                Cell(id = 0, type = EMPTY),
                Cell(id = 1, type = EMPTY),
                Cell(id = 2, type = CROSS),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = EMPTY),
                Cell(id = 5, type = CROSS),
                Cell(id = 6, type = EMPTY),
                Cell(id = 7, type = EMPTY),
                Cell(id = 8, type = CROSS),
            ),
            isCrossMove = true,
            expected = Victory(
                winner = CROSS,
                combination = listOf(2, 5, 8),
            ),
        ),
        WIN_BY_MAIN_DIAGONAL_CROSS(
            cells = listOf(
                Cell(id = 0, type = CROSS),
                Cell(id = 1, type = EMPTY),
                Cell(id = 2, type = EMPTY),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = CROSS),
                Cell(id = 5, type = EMPTY),
                Cell(id = 6, type = EMPTY),
                Cell(id = 7, type = EMPTY),
                Cell(id = 8, type = CROSS),
            ),
            isCrossMove = true,
            expected = Victory(
                winner = CROSS,
                combination = listOf(0, 4, 8),
            ),
        ),
        WIN_BY_SIDE_DIAGONAL_CROSS(
            cells = listOf(
                Cell(id = 0, type = EMPTY),
                Cell(id = 1, type = EMPTY),
                Cell(id = 2, type = CROSS),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = CROSS),
                Cell(id = 5, type = EMPTY),
                Cell(id = 6, type = CROSS),
                Cell(id = 7, type = EMPTY),
                Cell(id = 8, type = EMPTY),
            ),
            isCrossMove = true,
            expected = Victory(
                winner = CROSS,
                combination = listOf(2, 4, 6),
            ),
        ),
        WIN_BY_FIRST_ROW_ZERO(
            cells = listOf(
                Cell(id = 0, type = ZERO),
                Cell(id = 1, type = ZERO),
                Cell(id = 2, type = ZERO),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = EMPTY),
                Cell(id = 5, type = EMPTY),
                Cell(id = 6, type = EMPTY),
                Cell(id = 7, type = EMPTY),
                Cell(id = 8, type = EMPTY),
            ),
            isCrossMove = false,
            expected = Victory(
                winner = ZERO,
                combination = listOf(0, 1, 2),
            ),
        ),
        WIN_BY_SECOND_ROW_ZERO(
            cells = listOf(
                Cell(id = 0, type = EMPTY),
                Cell(id = 1, type = EMPTY),
                Cell(id = 2, type = EMPTY),
                Cell(id = 3, type = ZERO),
                Cell(id = 4, type = ZERO),
                Cell(id = 5, type = ZERO),
                Cell(id = 6, type = EMPTY),
                Cell(id = 7, type = EMPTY),
                Cell(id = 8, type = EMPTY),
            ),
            isCrossMove = false,
            expected = Victory(
                winner = ZERO,
                combination = listOf(3, 4, 5),
            ),
        ),
        WIN_BY_THIRD_ROW_ZERO(
            cells = listOf(
                Cell(id = 0, type = EMPTY),
                Cell(id = 1, type = EMPTY),
                Cell(id = 2, type = EMPTY),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = EMPTY),
                Cell(id = 5, type = EMPTY),
                Cell(id = 6, type = ZERO),
                Cell(id = 7, type = ZERO),
                Cell(id = 8, type = ZERO),
            ),
            isCrossMove = false,
            expected = Victory(
                winner = ZERO,
                combination = listOf(6, 7, 8),
            ),
        ),
        WIN_BY_FIRST_COLUMN_ZERO(
            cells = listOf(
                Cell(id = 0, type = ZERO),
                Cell(id = 1, type = EMPTY),
                Cell(id = 2, type = EMPTY),
                Cell(id = 3, type = ZERO),
                Cell(id = 4, type = EMPTY),
                Cell(id = 5, type = EMPTY),
                Cell(id = 6, type = ZERO),
                Cell(id = 7, type = EMPTY),
                Cell(id = 8, type = EMPTY),
            ),
            isCrossMove = false,
            expected = Victory(
                winner = ZERO,
                combination = listOf(0, 3, 6),
            ),
        ),
        WIN_BY_SECOND_COLUMN_ZERO(
            cells = listOf(
                Cell(id = 0, type = EMPTY),
                Cell(id = 1, type = ZERO),
                Cell(id = 2, type = EMPTY),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = ZERO),
                Cell(id = 5, type = EMPTY),
                Cell(id = 6, type = EMPTY),
                Cell(id = 7, type = ZERO),
                Cell(id = 8, type = EMPTY),
            ),
            isCrossMove = false,
            expected = Victory(
                winner = ZERO,
                combination = listOf(1, 4, 7),
            ),
        ),
        WIN_BY_THIRD_COLUMN_ZERO(
            cells = listOf(
                Cell(id = 0, type = EMPTY),
                Cell(id = 1, type = EMPTY),
                Cell(id = 2, type = ZERO),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = EMPTY),
                Cell(id = 5, type = ZERO),
                Cell(id = 6, type = EMPTY),
                Cell(id = 7, type = EMPTY),
                Cell(id = 8, type = ZERO),
            ),
            isCrossMove = false,
            expected = Victory(
                winner = ZERO,
                combination = listOf(2, 5, 8),
            ),
        ),
        WIN_BY_MAIN_DIAGONAL_ZERO(
            cells = listOf(
                Cell(id = 0, type = ZERO),
                Cell(id = 1, type = EMPTY),
                Cell(id = 2, type = EMPTY),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = ZERO),
                Cell(id = 5, type = EMPTY),
                Cell(id = 6, type = EMPTY),
                Cell(id = 7, type = EMPTY),
                Cell(id = 8, type = ZERO),
            ),
            isCrossMove = false,
            expected = Victory(
                winner = ZERO,
                combination = listOf(0, 4, 8),
            ),
        ),
        WIN_BY_SIDE_DIAGONAL_ZERO(
            cells = listOf(
                Cell(id = 0, type = EMPTY),
                Cell(id = 1, type = EMPTY),
                Cell(id = 2, type = ZERO),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = ZERO),
                Cell(id = 5, type = EMPTY),
                Cell(id = 6, type = ZERO),
                Cell(id = 7, type = EMPTY),
                Cell(id = 8, type = EMPTY),
            ),
            isCrossMove = false,
            expected = Victory(
                winner = ZERO,
                combination = listOf(2, 4, 6),
            ),
        ),
        DRAW(
            cells = listOf(
                Cell(id = 0, type = ZERO),
                Cell(id = 1, type = CROSS),
                Cell(id = 2, type = ZERO),
                Cell(id = 3, type = CROSS),
                Cell(id = 4, type = ZERO),
                Cell(id = 5, type = CROSS),
                Cell(id = 6, type = ZERO),
                Cell(id = 7, type = CROSS),
                Cell(id = 8, type = ZERO),
            ),
            isCrossMove = true,
            expected = Draw,
        ),
        CONTINUE(
            cells = listOf(
                Cell(id = 0, type = EMPTY),
                Cell(id = 1, type = CROSS),
                Cell(id = 2, type = ZERO),
                Cell(id = 3, type = CROSS),
                Cell(id = 4, type = ZERO),
                Cell(id = 5, type = CROSS),
                Cell(id = 6, type = ZERO),
                Cell(id = 7, type = CROSS),
                Cell(id = 8, type = ZERO),
            ),
            isCrossMove = true,
            expected = Continue,
        ),
        CONTINUE_DIAGONAL(
            cells = listOf(
                Cell(id = 0, type = CROSS),
                Cell(id = 1, type = EMPTY),
                Cell(id = 2, type = CROSS),
                Cell(id = 3, type = ZERO),
                Cell(id = 4, type = ZERO),
                Cell(id = 5, type = EMPTY),
                Cell(id = 6, type = CROSS),
                Cell(id = 7, type = EMPTY),
                Cell(id = 8, type = EMPTY),
            ),
            isCrossMove = true,
            expected = Continue,
        ),
    }

    @ParameterizedTest
    @EnumSource(Status::class)
    fun invoke(item: Status) {
        // action
        val action = getMatchStatusUseCase(
            cells = item.cells,
            fieldMode = FieldMode.THREE,
            isCrossMove = item.isCrossMove
        )

        // assert
        assertEquals(item.expected, action)
    }

    enum class Combination(
        val size: Int,
        val expected: List<List<Int>>,
    ) {
        THREE(
            size = 3,
            expected = listOf(
                listOf(0, 1, 2),
                listOf(0, 3, 6),
                listOf(3, 4, 5),
                listOf(1, 4, 7),
                listOf(6, 7, 8),
                listOf(2, 5, 8),
                listOf(0, 4, 8),
                listOf(2, 4, 6),
            ),
        ),
    }

    @ParameterizedTest
    @EnumSource(Combination::class)
    fun generateWinningCombinations(item: Combination) {
        // action
        val action = getMatchStatusUseCase.generateWinningCombinations(item.size)

        // assert
        assertEquals(item.expected, action)
    }
}