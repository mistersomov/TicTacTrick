package com.mistersomov.tictactrick.presentation.screen.match.viewmodel

import app.cash.turbine.test
import com.mistersomov.tictactrick.domain.entity.MatchStatus
import com.mistersomov.tictactrick.domain.entity.MatchStatus.Continue
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Global
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable
import com.mistersomov.tictactrick.domain.use_case.ApplyTrickyCardUseCase
import com.mistersomov.tictactrick.domain.use_case.GetMatchStatusUseCase
import com.mistersomov.tictactrick.domain.use_case.GetRandomTrickyCardUseCase
import com.mistersomov.tictactrick.domain.use_case.MoveUseCase
import com.mistersomov.tictactrick.presentation.TestDispatcherExtension
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Effect.ShowDialog
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Effect.ShowTrickyCardDetails
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Intent
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Intent.OnTrickyCardClicked
import com.mistersomov.tictactrick.presentation.screen.match.entity.board.CellUiEntity
import com.mistersomov.tictactrick.presentation.screen.match.entity.tricky_card.TrickyCardUiEntity
import com.mistersomov.tictactrick.presentation.screen.match.mutator.MatchStateMutator
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestDispatcherExtension::class)
class MatchViewModelTest {
    private val applyTrickyCardUseCase: ApplyTrickyCardUseCase = mockk()
    private val getMatchStatusUseCase: GetMatchStatusUseCase = mockk()
    private val moveUseCase: MoveUseCase = mockk()
    private val getRandomTrickyCardUseCase: GetRandomTrickyCardUseCase = mockk()
    private val mutator: MatchStateMutator = mockk()
    private val viewModel: MatchViewModel by lazy {
        MatchViewModel(
            applyTrickyCardUseCase = applyTrickyCardUseCase,
            getMatchStatusUseCase = getMatchStatusUseCase,
            moveUseCase = moveUseCase,
            getRandomTrickyCardUseCase = getRandomTrickyCardUseCase,
            mutator = mutator
        )
    }

    @AfterEach
    fun teardown() {
        confirmVerified(
            applyTrickyCardUseCase,
            getMatchStatusUseCase,
            moveUseCase,
            getRandomTrickyCardUseCase,
            mutator,
        )
    }

    @Test
    fun `sendIntent() - Move updates state`() = runTest {
        // mock
        val cell: CellUiEntity = mockk(relaxed = true) {
            every { id } returns 0
            every { isRevealed } returns false
            every { lockedRes } returns null
        }

        every { moveUseCase(any(), any(), any()) } returns listOf(mockk())
        every { getMatchStatusUseCase(any(), any()) } returns Continue
        every { mutator.mutate(any(), any()) } returns mockk()

        // action
        viewModel.sendIntent(Intent.Move(cell))

        // verify
        verify {
            moveUseCase(any(), any(), any())
            getMatchStatusUseCase(any(), any())
            mutator.mutate(any(), any())
        }
    }

    @Test
    fun `sendIntent() - Move skip change moving`() = runTest {
        // mock
        val cell: CellUiEntity = mockk(relaxed = true) {
            every { id } returns 0
            every { isRevealed } returns false
            every { lockedRes } returns 123
        }

        // action
        viewModel.sendIntent(Intent.Move(cell))

        // verify
        verify(exactly = 0) {
            moveUseCase(any(), any(), any())
            getMatchStatusUseCase(any(), any())
            mutator.mutate(any(), any())
        }
    }

    @Test
    fun `sendIntent() - ActivateTrickyCard updates state`() = runTest {
        // mock
        val card: TrickyCardUiEntity = mockk(relaxed = true) { every { card } returns mockk<Selectable>() }

        every { mutator.mutate(any(), any()) } returns mockk()

        // action
        viewModel.sendIntent(Intent.ActivateTrickyCard(card))

        // verify
        verify { mutator.mutate(any(), any()) }
    }

    @Test
    fun `sendIntent() - ActivateTrickyCard apply global tricky card`() = runTest {
        mockkConstructor(MatchViewModel::class) {
            // mock
            val card: TrickyCardUiEntity = mockk(relaxed = true) { every { card } returns mockk<Global>() }

            every { anyConstructed<MatchViewModel>().viewState } returns MutableStateFlow(
                mockk {
                    every { cells } returns listOf(mockk(relaxed = true))
                    every { trickyCardSelected } returns card
                }
            )
            every { applyTrickyCardUseCase(cells = any(), card = any()) } returns mockk()
            every { mutator.mutate(any(), any()) } returns mockk()

            // action
            viewModel.sendIntent(Intent.ActivateTrickyCard(card))

            // verify
            verify {
                applyTrickyCardUseCase(cells = any(), card = any())
                mutator.mutate(any(), any())
            }
        }
    }

    @Test
    fun `sendIntent() - Restart resets game state`() = runTest {
        // mock
        every { getRandomTrickyCardUseCase() } returns listOf(mockk())
        every { mutator.mutate(any(), any()) } returns mockk()

        // action
        viewModel.sendIntent(Intent.Restart)

        // verify
        verify {
            getRandomTrickyCardUseCase()
            mutator.mutate(any(), any())
        }
    }

    @Test
    fun `game over triggers ShowDialog effect`() = runTest {
        // mock
        val cell: CellUiEntity = mockk(relaxed = true) {
            every { id } returns 0
            every { isRevealed } returns false
            every { lockedRes } returns null
        }

        every { moveUseCase(cells = any(), index = 0, isCrossMove = true) } returns listOf(mockk())
        every { getMatchStatusUseCase(any(), any()) } returns MatchStatus.Draw
        every { mutator.mutate(any(), any()) } returns mockk()

        // action && assert
        viewModel.effect.test {
            viewModel.sendIntent(Intent.Move(cell))
            assertEquals(ShowDialog, awaitItem())
        }

        // verify
        verify {
            moveUseCase(cells = any(), index = 0, isCrossMove = true)
            getMatchStatusUseCase(any(), any())
            mutator.mutate(any(), any())
        }
    }

    @Test
    fun handleTrickyCardClicked() = runTest {
        // mock
        val model: TrickyCardUiEntity = mockk(relaxed = true)

        // action && assert
        viewModel.effect.test {
            viewModel.sendIntent(OnTrickyCardClicked(model))
            assertEquals(ShowTrickyCardDetails(model), awaitItem())
        }
    }
}