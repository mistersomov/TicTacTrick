package com.mistersomov.tictactrick.domain.use_case

import com.mistersomov.tictactrick.domain.entity.Cell
import com.mistersomov.tictactrick.domain.entity.CellType.CROSS
import com.mistersomov.tictactrick.domain.entity.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.CellType.ZERO
import com.mistersomov.tictactrick.domain.entity.GameStatus
import com.mistersomov.tictactrick.domain.entity.GameStatus.Continue
import com.mistersomov.tictactrick.domain.entity.GameStatus.Draw
import com.mistersomov.tictactrick.domain.entity.GameStatus.Victory
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import kotlin.test.assertEquals

internal class GetGameStatusUseCaseImplTest {
    private val getGameStatusUseCase = GetGameStatusUseCaseImpl()

    enum class Status(
        val cells: List<Cell>,
        val isCrossMove: Boolean,
        val expected: GameStatus,
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
            expected = Victory(winner = CROSS),
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
            expected = Victory(winner = CROSS),
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
            expected = Victory(winner = CROSS),
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
            expected = Victory(winner = CROSS),
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
            expected = Victory(winner = CROSS),
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
            expected = Victory(winner = CROSS),
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
            expected = Victory(winner = CROSS),
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
            expected = Victory(winner = CROSS),
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
            expected = Victory(winner = ZERO),
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
            expected = Victory(winner = ZERO),
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
            expected = Victory(winner = ZERO),
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
            expected = Victory(winner = ZERO),
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
            expected = Victory(winner = ZERO),
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
            expected = Victory(winner = ZERO),
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
            expected = Victory(winner = ZERO),
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
            expected = Victory(winner = ZERO),
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
        val action = getGameStatusUseCase(cells = item.cells, isCrossMove = item.isCrossMove)

        // assert
        assertEquals(item.expected, action)
    }
}