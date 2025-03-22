package com.mistersomov.tictactrick.presentation.screen.match.mapper

import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.entity.board.CellType
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard
import com.mistersomov.tictactrick.presentation.R
import com.mistersomov.tictactrick.presentation.screen.match.entity.board.CellUiEntity
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import kotlin.test.assertEquals

internal class CellMapperTest {

    enum class Cells(
        val cell: Cell,
        val trickyCard: TrickyCard?,
        val expected: CellUiEntity,
    ) {
        EMPTY_CELL(
            cell = mockk(relaxed = true) {
                every { id } returns 0
                every { type } returns CellType.EMPTY
            },
            trickyCard = null,
            expected = CellUiEntity(
                id = 0,
                imageRes = null,
                imageDescription = null,
                isRevealed = false,
                trickyCard = null,
                lockedRes = null,
            ),
        ),
        X(
            cell = mockk(relaxed = true) {
                every { id } returns 0
                every { type } returns CellType.CROSS
            },
            trickyCard = null,
            expected = CellUiEntity(
                id = 0,
                imageRes = R.drawable.cross,
                imageDescription = R.string.cross,
                isRevealed = true,
                trickyCard = null,
                lockedRes = null,
            ),
        ),
        X_WITH_TRICKY(
            cell = mockk(relaxed = true) {
                every { id } returns 0
                every { type } returns CellType.CROSS
            },
            trickyCard = TrickyCard.Selectable.SingleSelectable.Blaze(),
            expected = CellUiEntity(
                id = 0,
                imageRes = R.drawable.cross,
                imageDescription = R.string.cross,
                isRevealed = true,
                trickyCard = TrickyCard.Selectable.SingleSelectable.Blaze(),
                lockedRes = R.drawable.lava,
            ),
        ),
        O(
            cell = mockk(relaxed = true) {
                every { id } returns 0
                every { type } returns CellType.ZERO
            },
            trickyCard = null,
            expected = CellUiEntity(
                id = 0,
                imageRes = R.drawable.zero,
                imageDescription = R.string.zero,
                isRevealed = true,
                trickyCard = null,
                lockedRes = null,
            ),
        ),
    }

    @ParameterizedTest
    @EnumSource(Cells::class)
    fun toUi(cell: Cells) {
        // action
        val action = cell.cell.toUi(cell.trickyCard)

        // assert
        assertThat(action)
            .usingRecursiveComparison()
            .isEqualTo(cell.expected)
    }

    @Test
    fun toDomain() {
        // mock
        val uiModel: CellUiEntity = mockk(relaxed = true) {
            every { id } returns 123
            every { imageRes } returns R.drawable.cross
            every { trickyCard } returns TrickyCard.Selectable.SingleSelectable.Freezing()
        }
        val expected = Cell(
            id = 123,
            type = CellType.CROSS,
            trickyCard = TrickyCard.Selectable.SingleSelectable.Freezing(),
        )

        // action
        val action = uiModel.toDomain()

        // assert
        assertEquals(expected, action)
    }
}