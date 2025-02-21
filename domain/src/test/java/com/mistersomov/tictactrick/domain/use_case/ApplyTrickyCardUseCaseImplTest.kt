package com.mistersomov.tictactrick.domain.use_case

import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.entity.board.CellType.CROSS
import com.mistersomov.tictactrick.domain.entity.board.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.board.CellType.ZERO
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ApplyTrickyCardUseCaseImplTest {
    private val applyTrickyCardUseCase = ApplyTrickyCardUseCaseImpl()

    @Nested
    inner class Freezing {

        @Test
        fun `not empty field`() {
            // mock
            val cells: List<Cell> = listOf(
                Cell(id = 0, type = ZERO),
                Cell(id = 1, type = CROSS),
                Cell(id = 2, type = EMPTY),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = CROSS),
                Cell(id = 5, type = ZERO),
            )
            val freezingCard = TrickyCard.Freezing(cellId = 1)
            val expected: List<Cell> = listOf(
                Cell(id = 0, type = ZERO),
                Cell(id = 1, type = CROSS),
                Cell(id = 2, type = EMPTY),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = CROSS),
                Cell(id = 5, type = ZERO),
            )

            // action
            val action = applyTrickyCardUseCase(cells = cells, card = freezingCard)


            // assert
            assertEquals(expected, action)
        }

        @Test
        fun `locked field`() {
            // mock
            val cells: List<Cell> = listOf(
                Cell(id = 0, type = ZERO),
                Cell(id = 1, type = CROSS),
                Cell(id = 2, type = EMPTY, isLocked = true),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = CROSS),
                Cell(id = 5, type = ZERO),
            )
            val freezingCard = TrickyCard.Freezing(cellId = 2)
            val expected: List<Cell> = listOf(
                Cell(id = 0, type = ZERO),
                Cell(id = 1, type = CROSS),
                Cell(
                    id = 2,
                    type = EMPTY,
                    isLocked = true,
                ),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = CROSS),
                Cell(id = 5, type = ZERO),
            )

            // action
            val action = applyTrickyCardUseCase(cells = cells, card = freezingCard)


            // assert
            assertEquals(expected, action)
        }

        @Test
        fun `empty field`() {
            // mock
            val cells: List<Cell> = listOf(
                Cell(id = 0, type = ZERO),
                Cell(id = 1, type = CROSS),
                Cell(id = 2, type = EMPTY),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = CROSS),
                Cell(id = 5, type = ZERO),
            )
            val freezingCard = TrickyCard.Freezing(cellId = 2)
            val expected: List<Cell> = listOf(
                Cell(id = 0, type = ZERO),
                Cell(id = 1, type = CROSS),
                Cell(
                    id = 2,
                    type = EMPTY,
                    isLocked = true,
                ),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = CROSS),
                Cell(id = 5, type = ZERO),
            )

            // action
            val action = applyTrickyCardUseCase(cells = cells, card = freezingCard)


            // assert
            assertEquals(expected, action)
        }
    }
}