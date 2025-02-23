package com.mistersomov.tictactrick.domain.use_case

import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.entity.board.CellType.CROSS
import com.mistersomov.tictactrick.domain.entity.board.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.board.CellType.ZERO
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.Freezing
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.Tornado
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ActivateTrickyCardUseCaseImplTest {
    private val activateTrickyCardUseCase = ActivateTrickyCardUseCaseImpl()

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
            val freezingCard = Freezing(sourceId = 1)
            val expected: List<Cell> = listOf(
                Cell(id = 0, type = ZERO),
                Cell(id = 1, type = CROSS),
                Cell(id = 2, type = EMPTY),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = CROSS),
                Cell(id = 5, type = ZERO),
            )

            // action
            val action = activateTrickyCardUseCase(cells = cells, card = freezingCard)


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
            val freezing = Freezing(sourceId = 2)
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
            val action = activateTrickyCardUseCase(cells = cells, card = freezing)


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
            val freezing = Freezing(sourceId = 2)
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
            val action = activateTrickyCardUseCase(cells = cells, card = freezing)


            // assert
            assertEquals(expected, action)
        }
    }

    @Nested
    inner class Tornado {

        @Test
        fun `not found indices`() {
            // mock
            val cells: List<Cell> = listOf(
                Cell(id = 0),
                Cell(id = 5),
            )
            val tornado = Tornado(sourceId = -1, targetId = 100)
            val expected: List<Cell> = listOf(
                Cell(id = 0),
                Cell(id = 5),
            )

            // action
            val action = activateTrickyCardUseCase(cells = cells, card = tornado)

            // assert
            assertEquals(expected, action)
        }

        @Test
        fun `found indices`() {
            // mock
            val cells: List<Cell> = listOf(
                Cell(id = 0),
                Cell(id = 1, type = CROSS),
                Cell(id = 2, type = ZERO),
            )
            val tornado = Tornado(sourceId = 1, targetId = 2)
            val expected: List<Cell> = listOf(
                Cell(id = 0),
                Cell(id = 1, type = ZERO),
                Cell(id = 2, type = CROSS),
            )

            // action
            val action = activateTrickyCardUseCase(cells = cells, card = tornado)

            // assert
            assertEquals(expected, action)
        }
    }
}