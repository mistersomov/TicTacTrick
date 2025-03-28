package com.mistersomov.tictactrick.domain.use_case

import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.entity.board.CellType.CROSS
import com.mistersomov.tictactrick.domain.entity.board.CellType.EMPTY
import com.mistersomov.tictactrick.domain.entity.board.CellType.ZERO
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Global.Harmony
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.DualSelectable.Tornado
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.SingleSelectable.Blaze
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.SingleSelectable.Freezing
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

internal class ApplyTrickyCardUseCaseImplTest {
    private val useCase = ApplyTrickyCardUseCaseImpl()

    @Test
    fun `invoke() - Freezing on empty cell`() {
        // mock
        val cells = listOf(
            Cell(0, EMPTY),
            Cell(1, CROSS),
            Cell(2, EMPTY),
        )
        val card = Freezing(sourceId = 2)
        val expected = listOf(
            Cell(0, EMPTY),
            Cell(1, CROSS),
            Cell(
                id = 2,
                type = EMPTY,
                trickyCard = Freezing(2),
                remainingMoves = 3,
            ),
        )

        // action
        val action = useCase(cells, card)

        // assert
        assertEquals(expected, action)
    }

    @Test
    fun `invoke() - Freezing on empty cell and update remaining moves`() {
        // mock
        val trickyCard = Blaze()
        val cells = listOf(
            Cell(0, EMPTY),
            Cell(
                id = 1,
                type = CROSS,
                trickyCard = trickyCard,
                remainingMoves = 2,
            ),
            Cell(2, EMPTY),
        )
        val card = Freezing(sourceId = 2)
        val expected = listOf(
            Cell(0, EMPTY),
            Cell(
                id = 1,
                type = CROSS,
                trickyCard = trickyCard,
                remainingMoves = 1,
            ),
            Cell(
                id = 2,
                type = EMPTY,
                trickyCard = Freezing(2),
                remainingMoves = 3,
            ),
        )

        // action
        val action = useCase(cells, card)

        // assert
        assertEquals(expected, action)
    }

    @Test
    fun `invoke() - Freezing on non-empty cell should do nothing`() {
        // mock
        val cells = listOf(
            Cell(0, EMPTY),
            Cell(1, CROSS),
            Cell(2, ZERO),
        )
        val card = Freezing(sourceId = 2)

        // action
        val action = useCase(cells, card)

        // assert
        assertEquals(cells, action)
    }

    @Test
    fun `invoke() - Blaze on empty cell`() {
        // mock
        val cells = listOf(
            Cell(0, EMPTY),
            Cell(1, CROSS),
            Cell(2, EMPTY),
        )
        val card = Blaze(sourceId = 2)
        val expected = listOf(
            Cell(0, EMPTY),
            Cell(1, CROSS),
            Cell(
                id = 2,
                type = EMPTY,
                trickyCard = Blaze(2),
                remainingMoves = 3,
            ),
        )

        // action
        val action = useCase(cells, card)

        // assert
        assertEquals(expected, action)
    }

    @Test
    fun `invoke() - Tornado swaps two existing cells`() {
        // mock
        val cells = listOf(
            Cell(0, EMPTY),
            Cell(1, CROSS),
            Cell(2, ZERO),
        )
        val card = Tornado(sourceId = 1, targetId = 2)
        val expected = listOf(
            Cell(0, EMPTY),
            Cell(1, ZERO),
            Cell(2, CROSS),
        )

        // action
        val action = useCase(cells, card)

        // assert
        assertEquals(expected, action)
    }

    @Test
    fun `invoke() - Tornado with invalid indices does nothing`() {
        // mock
        val cells = listOf(
            Cell(0, EMPTY),
            Cell(1, CROSS),
            Cell(2, ZERO),
        )
        val card = Tornado(sourceId = 3, targetId = 4)

        // action
        val action = useCase(cells, card)

        // assert
        assertEquals(cells, action)
    }

    @Test
    fun `invoke() - Harmony removes all tricky cards`() {
        // mock
        val cells = listOf(
            Cell(0, EMPTY, trickyCard = Blaze(0)),
            Cell(1, CROSS, trickyCard = Freezing(1)),
            Cell(2, ZERO, trickyCard = Tornado(1, 2)),
        )
        val expected = listOf(
            Cell(0, EMPTY, trickyCard = null),
            Cell(1, CROSS, trickyCard = null),
            Cell(2, ZERO, trickyCard = null),
        )

        // action
        val action = useCase(cells, Harmony)

        // assert
        assertEquals(expected, action)
    }
}