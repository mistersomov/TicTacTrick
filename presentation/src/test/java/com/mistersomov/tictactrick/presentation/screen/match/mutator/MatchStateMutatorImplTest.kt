package com.mistersomov.tictactrick.presentation.screen.match.mutator

import com.mistersomov.tictactrick.domain.entity.MatchStatus.Continue
import com.mistersomov.tictactrick.domain.entity.board.BoardMode.FOUR
import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.entity.board.CellType.CROSS
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.DualSelectable.Tornado
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.SingleSelectable.Freezing
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.State
import com.mistersomov.tictactrick.presentation.screen.match.entity.board.CellUiEntity
import com.mistersomov.tictactrick.presentation.screen.match.entity.tricky_card.TrickyCardUiEntity
import com.mistersomov.tictactrick.presentation.screen.match.mutator.MatchMutatorEvent.ActivateTrickyCard
import com.mistersomov.tictactrick.presentation.screen.match.mutator.MatchMutatorEvent.ApplyTrickyCard
import com.mistersomov.tictactrick.presentation.screen.match.mutator.MatchMutatorEvent.Move
import com.mistersomov.tictactrick.presentation.screen.match.mutator.MatchMutatorEvent.StartMatch
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MatchStateMutatorImplTest {
    private val mutator = MatchStateMutatorImpl()

    @Test
    fun `mutate() - Start match`() {
        // mock
        val currentState = State(
            cells = listOf(CellUiEntity(0), CellUiEntity(1)),
            isCrossMove = false,
            trickyCards = listOf(mockk()),
            gameOver = true,
        )

        // action
        val action = mutator.mutate(
            currentState = currentState,
            event = StartMatch(mode = FOUR, trickyCards = listOf(
                Tornado(),
                Freezing(),
            ))
        )

        // assert
        assertThat(action.cells.size).isEqualTo(16)
        assertThat(action.isCrossMove).isTrue()
        assertThat(action.trickyCards.size).isEqualTo(2)
        assertThat(action.gameOver).isFalse()
    }

    @Test
    fun `mutate() - Move updates cells, match status and turn`() {
        // mock
        val currentState = State(
            cells = listOf(CellUiEntity(0), CellUiEntity(1)),
            isCrossMove = true,
        )
        val updatedCells: List<Cell> = listOf(Cell(id  = 0, type = CROSS), Cell(id = 1))

        // action
        val action = mutator.mutate(
            currentState = currentState,
            event = Move(updatedCells = updatedCells, matchStatus = Continue)
        )

        // assert
        assertThat(action.cells[0].id).isEqualTo(0)
        assertThat(action.cells[0].isRevealed).isTrue()
        assertThat(action.isCrossMove).isFalse()
        assertThat(action.matchStatus).isEqualTo(Continue)
    }

    @Test
    fun `mutate() - ActivateTrickyCard hides selected card and disables others`() {
        // mock
        val trickyCard1: TrickyCardUiEntity = mockk(relaxed = true) { every { card } returns Freezing(2) }
        val trickyCard2: TrickyCardUiEntity = mockk(relaxed = true)
        val currentState = State(
            trickyCards = listOf(
                trickyCard1,
                trickyCard2,
            ),
            trickyCardSelected = null,
        )
        val expected = State(
            trickyCards = listOf(
                trickyCard1.copy(isVisible = false),
                trickyCard2.copy(isEnabled = false),
            ),
            trickyCardSelected = trickyCard1,
        )

        // action
        val action = mutator.mutate(
            currentState = currentState,
            event = ActivateTrickyCard(trickyCard = trickyCard1),
        )

        // assert
        assertThat(action)
            .usingRecursiveComparison()
            .comparingOnlyFields(
                "trickyCards",
                "trickyCardSelected",
            )
            .isEqualTo(expected)
    }

    @Test
    fun `mutate() - ApplyTrickyCard updates cells and resets tricky card state`() {
        // mock
        val trickyCard1: TrickyCardUiEntity = mockk(relaxed = true) { every { isEnabled } returns false }
        val trickyCard2: TrickyCardUiEntity = mockk(relaxed = true)
        val currentState = State(
            selectedCells = listOf(mockk(), mockk()),
            trickyCards = listOf(trickyCard1, trickyCard2),
            trickyCardSelected = trickyCard2,
        )
        val expected = State(
            selectedCells = emptyList(),
            trickyCards = listOf(trickyCard1.copy(isEnabled = true), trickyCard2),
            trickyCardSelected = null,
        )

        // action
        val action = mutator.mutate(
            currentState = currentState,
            event = ApplyTrickyCard(updatedCells = emptyList(), matchStatus = Continue)
        )

        // assert
        assertThat(action)
            .usingRecursiveComparison()
            .comparingOnlyFields(
                "selectedCells",
                "trickyCards",
                "trickyCardSelected",
            )
            .isEqualTo(expected)
    }

}