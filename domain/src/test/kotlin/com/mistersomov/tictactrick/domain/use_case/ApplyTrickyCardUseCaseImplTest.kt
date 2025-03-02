package com.mistersomov.tictactrick.domain.use_case

import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.entity.board.CellType.CROSS
import com.mistersomov.tictactrick.domain.entity.board.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.board.CellType.ZERO
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Global.Harmony
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.DualSelectable.Tornado
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.SingleSelectable.Blaze
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.SingleSelectable.Freezing
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ApplyTrickyCardUseCaseImplTest {
    private val applyTrickyCardUseCase = ApplyTrickyCardUseCaseImpl()

    @Nested
    inner class SingleSelectable {

        @Test
        fun `invoke() - Freezing and not empty field`() {
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
            val action = applyTrickyCardUseCase(cells = cells, card = freezingCard)


            // assert
            assertEquals(expected, action)
        }

        @Test
        fun `invoke() - Freezing and locked field`() {
            // mock
            val cells: List<Cell> = listOf(
                Cell(id = 0, type = ZERO),
                Cell(id = 1, type = CROSS),
                Cell(id = 2, type = EMPTY, isFrozen = true),
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
                    isFrozen = true,
                ),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = CROSS),
                Cell(id = 5, type = ZERO),
            )

            // action
            val action = applyTrickyCardUseCase(cells = cells, card = freezing)


            // assert
            assertEquals(expected, action)
        }

        @Test
        fun `invoke() - Freezing and empty field`() {
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
                    isFrozen = true,
                ),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = CROSS),
                Cell(id = 5, type = ZERO),
            )

            // action
            val action = applyTrickyCardUseCase(cells = cells, card = freezing)


            // assert
            assertEquals(expected, action)
        }

        @Test
        fun `invoke() - Blaze and empty field`() {
            // mock
            val cells: List<Cell> = listOf(
                Cell(id = 0, type = ZERO),
                Cell(id = 1, type = CROSS),
                Cell(id = 2, type = EMPTY),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = CROSS),
                Cell(id = 5, type = ZERO),
            )
            val expected: List<Cell> = listOf(
                Cell(id = 0, type = ZERO),
                Cell(id = 1, type = CROSS),
                Cell(
                    id = 2,
                    type = EMPTY,
                    isBlazed = true,
                ),
                Cell(id = 3, type = EMPTY),
                Cell(id = 4, type = CROSS),
                Cell(id = 5, type = ZERO),
            )

            // action
            val action = applyTrickyCardUseCase(cells = cells, card = Blaze(sourceId = 2))


            // assert
            assertEquals(expected, action)
        }
    }

    @Nested
    inner class Tornado {

        @Test
        fun `invoke() - Not found indices`() {
            // mock
            val cells: List<Cell> = listOf(
                Cell(id = 0),
                Cell(id = 5),
            )
            val tornado =
                Tornado(sourceId = -1, targetId = 100)
            val expected: List<Cell> = listOf(
                Cell(id = 0),
                Cell(id = 5),
            )

            // action
            val action = applyTrickyCardUseCase(cells = cells, card = tornado)

            // assert
            assertEquals(expected, action)
        }

        @Test
        fun `invoke() - Found indices`() {
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
            val action = applyTrickyCardUseCase(cells = cells, card = tornado)

            // assert
            assertEquals(expected, action)
        }
    }

    @Test
    fun `invoke() - Apply Harmony and unlocked fields`() {
        // mock
        val cell1: Cell = mockk(relaxed = true) { every { isFrozen } returns true }
        val cell2: Cell = mockk(relaxed = true) { every { isFrozen } returns true }
        val cell3: Cell = mockk(relaxed = true) { every { isFrozen } returns false }
        val cells: List<Cell> = listOf(cell1, cell2, cell3)

        // action
        val action = applyTrickyCardUseCase(cells, Harmony)

        // assert
        assertThat(action.any { it.isFrozen }).isFalse()
    }
}