package com.mistersomov.happyandhealth.presentation.screen.match.viewmodel

import com.mistersomov.happyandhealth.presentation.TestDispatcherExtension
import com.mistersomov.tictactrick.domain.entity.MatchStatus.Continue
import com.mistersomov.tictactrick.domain.entity.MatchStatus.Draw
import com.mistersomov.tictactrick.domain.entity.MatchStatus.Victory
import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.entity.board.CellType.CROSS
import com.mistersomov.tictactrick.domain.use_case.GetMatchStatusUseCase
import com.mistersomov.tictactrick.domain.use_case.MoveUseCase
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Intent.Move
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Intent.StartGame
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.State
import com.mistersomov.tictactrick.presentation.screen.match.viewmodel.MatchViewModel
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

@ExtendWith(TestDispatcherExtension::class)
internal class MatchViewModelTest {
    private val moveUseCase: MoveUseCase = mockk()
    private val getMatchStatusUseCase: GetMatchStatusUseCase = mockk()
    private val viewModel by lazy {
        MatchViewModel(
            moveUseCase = moveUseCase,
            getMatchStatusUseCase = getMatchStatusUseCase,
        )
    }

    @AfterEach
    fun teardown() {
        confirmVerified(
            moveUseCase,
            getMatchStatusUseCase,
        )
    }

    @Test
    fun `start game`() {
        // mock
        val expected = State(
            cells = listOf(
                Cell(0),
                Cell(1),
                Cell(2),
                Cell(3),
                Cell(4),
                Cell(5),
                Cell(6),
                Cell(7),
                Cell(8),
            ),
        )

        // action
        viewModel.sendIntent(StartGame)

        // assert
        assertEquals(expected, viewModel.viewState.value)
    }

    @Nested
    inner class Move {

        @Test
        fun victory() {
            // mock
            val expected = State(
                matchStatus = Victory(winner = CROSS, combination = listOf(1, 2, 3)),
            )

            every {
                moveUseCase.invoke(
                    cells = any(),
                    index = 0,
                    isCrossMove = any(),
                )
            } returns listOf(mockk())
            every { getMatchStatusUseCase.invoke(
                cells = any(),
                boardMode = any(),
                isCrossMove = any(),
            ) } returns Victory(winner = CROSS, combination = listOf(1, 2, 3))

            // action
            viewModel.sendIntent(Move(0))

            // assert && verify
            assertThat(viewModel.viewState.value)
                .usingRecursiveComparison()
                .comparingOnlyFields(
                    "gameStatus",
                    "showReset",
                )
                .isEqualTo(expected)
            verify {
                moveUseCase.invoke(cells = any(), index = 0, isCrossMove = any())
                getMatchStatusUseCase.invoke(cells = any(), boardMode = any(), isCrossMove = any())
            }
        }

        @Test
        fun draw() {
            // mock
            val expected = State(matchStatus = Draw)

            every {
                moveUseCase.invoke(
                    cells = any(),
                    index = 0,
                    isCrossMove = any(),
                )
            } returns listOf(mockk())
            every { getMatchStatusUseCase.invoke(
                cells = any(),
                boardMode = any(),
                isCrossMove = any(),
            ) } returns Draw

            // action
            viewModel.sendIntent(Move(0))

            // assert && verify
            assertThat(viewModel.viewState.value)
                .usingRecursiveComparison()
                .comparingOnlyFields(
                    "gameStatus",
                    "showReset",
                )
                .isEqualTo(expected)
            verify {
                moveUseCase.invoke(cells = any(), index = 0, isCrossMove = any())
                getMatchStatusUseCase.invoke(cells = any(), boardMode = any(), isCrossMove = any())
            }
        }

        @Test
        fun `continue`() {
            // mock
            val expected = State(matchStatus = Continue, isCrossMove = false)

            every {
                moveUseCase.invoke(
                    cells = any(),
                    index = 0,
                    isCrossMove = any(),
                )
            } returns listOf(mockk())
            every { getMatchStatusUseCase.invoke(
                cells = any(),
                boardMode = any(),
                isCrossMove = any(),
            ) } returns Continue

            // action
            viewModel.sendIntent(Move(0))

            // assert && verify
            assertThat(viewModel.viewState.value)
                .usingRecursiveComparison()
                .comparingOnlyFields(
                    "gameStatus",
                    "isCrossMove",
                )
                .isEqualTo(expected)
            verify {
                moveUseCase.invoke(cells = any(), index = 0, isCrossMove = any())
                getMatchStatusUseCase.invoke(cells = any(), boardMode = any(), isCrossMove = any())
            }
        }
    }
}