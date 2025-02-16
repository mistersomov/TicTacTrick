package com.mistersomov.happyandhealth.presentation.viewmodel

import com.mistersomov.happyandhealth.presentation.TestDispatcherExtension
import com.mistersomov.tictactrick.domain.entity.Cell
import com.mistersomov.tictactrick.domain.entity.CellType.CROSS
import com.mistersomov.tictactrick.domain.entity.GameStatus.Continue
import com.mistersomov.tictactrick.domain.entity.GameStatus.Draw
import com.mistersomov.tictactrick.domain.entity.GameStatus.Victory
import com.mistersomov.tictactrick.domain.use_case.GetGameStatusUseCase
import com.mistersomov.tictactrick.domain.use_case.MoveUseCase
import com.mistersomov.tictactrick.presentation.view.GameContract.Intent.Move
import com.mistersomov.tictactrick.presentation.view.GameContract.Intent.StartGame
import com.mistersomov.tictactrick.presentation.view.GameContract.State
import com.mistersomov.tictactrick.presentation.viewmodel.GameViewModel
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
internal class GameViewModelTest {
    private val moveUseCase: MoveUseCase = mockk()
    private val getGameStatusUseCase: GetGameStatusUseCase = mockk()
    private val viewModel by lazy {
        GameViewModel(
            moveUseCase = moveUseCase,
            getGameStatusUseCase = getGameStatusUseCase,
        )
    }

    @AfterEach
    fun teardown() {
        confirmVerified(
            moveUseCase,
            getGameStatusUseCase,
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
                gameStatus = Victory(winner = CROSS, combination = listOf(1, 2, 3)),
            )

            every {
                moveUseCase.invoke(
                    cells = any(),
                    index = 0,
                    isCrossMove = any(),
                )
            } returns listOf(mockk())
            every { getGameStatusUseCase.invoke(
                cells = any(),
                fieldMode = any(),
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
                getGameStatusUseCase.invoke(cells = any(), fieldMode = any(), isCrossMove = any())
            }
        }

        @Test
        fun draw() {
            // mock
            val expected = State(gameStatus = Draw)

            every {
                moveUseCase.invoke(
                    cells = any(),
                    index = 0,
                    isCrossMove = any(),
                )
            } returns listOf(mockk())
            every { getGameStatusUseCase.invoke(
                cells = any(),
                fieldMode = any(),
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
                getGameStatusUseCase.invoke(cells = any(), fieldMode = any(), isCrossMove = any())
            }
        }

        @Test
        fun `continue`() {
            // mock
            val expected = State(gameStatus = Continue, isCrossMove = false)

            every {
                moveUseCase.invoke(
                    cells = any(),
                    index = 0,
                    isCrossMove = any(),
                )
            } returns listOf(mockk())
            every { getGameStatusUseCase.invoke(
                cells = any(),
                fieldMode = any(),
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
                getGameStatusUseCase.invoke(cells = any(), fieldMode = any(), isCrossMove = any())
            }
        }
    }
}