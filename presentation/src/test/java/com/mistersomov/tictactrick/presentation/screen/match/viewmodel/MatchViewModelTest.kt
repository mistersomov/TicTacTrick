package com.mistersomov.tictactrick.presentation.screen.match.viewmodel

import app.cash.turbine.test
import com.mistersomov.tictactrick.domain.entity.MatchStatus
import com.mistersomov.tictactrick.domain.entity.MatchStatus.Continue
import com.mistersomov.tictactrick.domain.use_case.ApplyTrickyCardUseCase
import com.mistersomov.tictactrick.domain.use_case.GetMatchStatusUseCase
import com.mistersomov.tictactrick.domain.use_case.MoveUseCase
import com.mistersomov.tictactrick.presentation.TestDispatcherExtension
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Effect.ShowDialog
import com.mistersomov.tictactrick.presentation.screen.match.MatchContract.Intent
import com.mistersomov.tictactrick.presentation.screen.match.entity.board.CellUiEntity
import com.mistersomov.tictactrick.presentation.screen.match.mutator.MatchStateMutator
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(TestDispatcherExtension::class)
class MatchViewModelTest {
    private val applyTrickyCardUseCase: ApplyTrickyCardUseCase = mockk()
    private val getMatchStatusUseCase: GetMatchStatusUseCase = mockk()
    private val moveUseCase: MoveUseCase = mockk()
    private val mutator: MatchStateMutator = mockk()
    private val viewModel: MatchViewModel by lazy {
        MatchViewModel(
            applyTrickyCardUseCase = applyTrickyCardUseCase,
            getMatchStatusUseCase = getMatchStatusUseCase,
            moveUseCase = moveUseCase,
            mutator = mutator
        )
    }

    @Test
    fun `sendIntent Move updates state`() = runTest {
        // mock
        val cell: CellUiEntity = mockk(relaxed = true) {
            every { id } returns 0
            every { isRevealed } returns false
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
    fun `sendIntent ActivateTrickyCard updates state`() = runTest {
        // mock
        every { mutator.mutate(any(), any()) } returns mockk()

        // action
        viewModel.sendIntent(Intent.ActivateTrickyCard(mockk(relaxed = true)))

        // verify
        verify { mutator.mutate(any(), any()) }
    }

    @Test
    fun `sendIntent Restart resets game state`() = runTest {
        // mock
        every { mutator.mutate(any(), any()) } returns mockk()

        // action
        viewModel.sendIntent(Intent.Restart)

        // verify
        verify { mutator.mutate(any(), any()) }
    }

    @Test
    fun `game over triggers ShowDialog effect`() = runTest {
        // mock
        val cell: CellUiEntity = mockk(relaxed = true) {
            every { id } returns 0
            every { isRevealed } returns false
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
}
