package com.mistersomov.tictactrick.domain.use_case

import com.mistersomov.tictactrick.domain.entity.Cell
import com.mistersomov.tictactrick.domain.entity.CellType.CROSS
import com.mistersomov.tictactrick.domain.entity.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.CellType.ZERO
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import kotlin.test.assertEquals

internal class MoveUseCaseImplTest {
    val moveUseCase = MoveUseCaseImpl()

    enum class Cells(
        val cells: List<Cell>,
        val index: Int,
        val isCrossMove: Boolean,
        val expected: List<Cell>,
    ) {
        NOT_EMPTY_CELL(
            cells = listOf(
                Cell(id = 0, type = CROSS),
                Cell(id = 1, type = EMPTY),
                Cell(id = 2, type = ZERO),
                Cell(id = 3, type = ZERO),
                Cell(id = 4, type = CROSS),
                Cell(id = 5, type = ZERO),
                Cell(id = 6, type = CROSS),
                Cell(id = 7, type = ZERO),
                Cell(id = 8, type = CROSS),
            ),
            index = 3,
            isCrossMove = false,
            expected = listOf(
                Cell(id = 0, type = CROSS),
                Cell(id = 1, type = EMPTY),
                Cell(id = 2, type = ZERO),
                Cell(id = 3, type = ZERO),
                Cell(id = 4, type = CROSS),
                Cell(id = 5, type = ZERO),
                Cell(id = 6, type = CROSS),
                Cell(id = 7, type = ZERO),
                Cell(id = 8, type = CROSS),
            ),
        ),
        EMPTY_CROSS(
            cells = listOf(
                Cell(id = 0, type = CROSS),
                Cell(id = 1, type = EMPTY),
                Cell(id = 2, type = ZERO),
                Cell(id = 3, type = ZERO),
                Cell(id = 4, type = CROSS),
                Cell(id = 5, type = ZERO),
                Cell(id = 6, type = CROSS),
                Cell(id = 7, type = ZERO),
                Cell(id = 8, type = CROSS),
            ),
            index = 1,
            isCrossMove = true,
            expected = listOf(
                Cell(id = 0, type = CROSS),
                Cell(id = 1, type = CROSS),
                Cell(id = 2, type = ZERO),
                Cell(id = 3, type = ZERO),
                Cell(id = 4, type = CROSS),
                Cell(id = 5, type = ZERO),
                Cell(id = 6, type = CROSS),
                Cell(id = 7, type = ZERO),
                Cell(id = 8, type = CROSS),
            ),
        ),
        EMPTY_ZERO(
            cells = listOf(
                Cell(id = 0, type = CROSS),
                Cell(id = 1, type = EMPTY),
                Cell(id = 2, type = ZERO),
                Cell(id = 3, type = ZERO),
                Cell(id = 4, type = CROSS),
                Cell(id = 5, type = ZERO),
                Cell(id = 6, type = CROSS),
                Cell(id = 7, type = EMPTY),
                Cell(id = 8, type = CROSS),
            ),
            index = 7,
            isCrossMove = false,
            expected = listOf(
                Cell(id = 0, type = CROSS),
                Cell(id = 1, type = EMPTY),
                Cell(id = 2, type = ZERO),
                Cell(id = 3, type = ZERO),
                Cell(id = 4, type = CROSS),
                Cell(id = 5, type = ZERO),
                Cell(id = 6, type = CROSS),
                Cell(id = 7, type = ZERO),
                Cell(id = 8, type = CROSS),
            ),
        ),
    }

    @ParameterizedTest
    @EnumSource(Cells::class)
    fun invoke(item: Cells) {
        // action
        val action = moveUseCase(
            cells = item.cells,
            index = item.index,
            isCrossMove = item.isCrossMove,
        )

        // assert
        assertEquals(item.expected, action)
    }
}