package com.mistersomov.tictactrick.presentation.screen.match.mapper

import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Global.Harmony
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.DualSelectable.Tornado
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.SingleSelectable.Blaze
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.SingleSelectable.Freezing
import com.mistersomov.tictactrick.presentation.R
import com.mistersomov.tictactrick.presentation.common.sound.SoundEffect
import com.mistersomov.tictactrick.presentation.screen.match.entity.tricky_card.TrickyCardUiEntity

fun TrickyCard.toUi(): TrickyCardUiEntity =
    when (this) {
        is Freezing -> {
            TrickyCardUiEntity(
                imageRes = R.drawable.freeze,
                imageDescription = R.string.tricky_card_freezing,
                description = R.string.tricky_card_freezing_description,
                card = this,
                soundEffectRes = R.raw.ice,
            )
        }
        is Blaze -> {
            TrickyCardUiEntity(
                imageRes = R.drawable.blaze,
                imageDescription = R.string.tricky_card_blaze,
                description = R.string.tricky_card_blaze_description,
                card = this,
                soundEffectRes = R.raw.blaze,
            )
        }
        is Tornado -> {
            TrickyCardUiEntity(
                imageRes = R.drawable.tornado,
                imageDescription = R.string.tricky_card_tornado,
                description = R.string.tricky_card_tornado_description,
                card = this,
                soundEffectRes = R.raw.tornado,
            )
        }
        is Harmony -> {
            TrickyCardUiEntity(
                imageRes = R.drawable.harmony,
                imageDescription = R.string.tricky_card_harmony,
                description = R.string.tricky_card_harmony_description,
                card = this,
                soundEffectRes = R.raw.harmony,
            )
        }
    }

val TrickyCardUiEntity.asSoundEffect: SoundEffect
    get() = object : SoundEffect {
        override val resId: Int = soundEffectRes
    }