package com.mistersomov.tictactrick.presentation.screen.match.mapper

import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard
import com.mistersomov.tictactrick.presentation.R
import com.mistersomov.tictactrick.presentation.screen.match.entity.tricky_card.TrickyCardUiEntity
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

internal class TrickyCardMapperTest {

    enum class TrickyType(
        val card: TrickyCard,
        val expected: TrickyCardUiEntity
    ) {
        FREEZE(
            card = TrickyCard.Selectable.SingleSelectable.Freezing(),
            expected = TrickyCardUiEntity(
                imageRes = R.drawable.freeze,
                imageDescription = R.string.tricky_card_freezing,
                description = R.string.tricky_card_freezing_description,
                card = mockk(relaxed = true),
                soundEffectRes = R.raw.ice,
            )
        ),
        BLAZE(
            card = TrickyCard.Selectable.SingleSelectable.Blaze(),
            expected = TrickyCardUiEntity(
                imageRes = R.drawable.blaze,
                imageDescription = R.string.tricky_card_blaze,
                description = R.string.tricky_card_blaze_description,
                card = mockk(relaxed = true),
                soundEffectRes = R.raw.blaze,
            )
        ),
        TORNADO(
            card = TrickyCard.Selectable.DualSelectable.Tornado(),
            expected = TrickyCardUiEntity(
                imageRes = R.drawable.tornado,
                imageDescription = R.string.tricky_card_tornado,
                description = R.string.tricky_card_tornado_description,
                card = mockk(relaxed = true),
                soundEffectRes = R.raw.tornado,
            )
        ),
        HARMONY(
            card = TrickyCard.Global.Harmony,
            expected = TrickyCardUiEntity(
                imageRes = R.drawable.harmony,
                imageDescription = R.string.tricky_card_harmony,
                description = R.string.tricky_card_harmony_description,
                card = mockk(relaxed = true),
                soundEffectRes = R.raw.harmony,
            )
        ),
    }

    @ParameterizedTest
    @EnumSource(TrickyType::class)
    fun toUi(card: TrickyType) {
        // action
        val action = card.card.toUi()

        // assert
        assertThat(action)
            .usingRecursiveComparison()
            .ignoringFields("card")
            .isEqualTo(card.expected)
    }
}