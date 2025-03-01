package com.mistersomov.tictactrick.presentation.screen.match.entity.tricky_card

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Global.Harmony
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.Freezing
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard.Selectable.Tornado
import com.mistersomov.tictactrick.presentation.R

data class TrickyCardUiEntity(
    @DrawableRes
    val imageRes: Int,
    @StringRes
    val imageDescription: Int,
    val card: TrickyCard,
    val isVisible: Boolean = true,
    val isEnabled: Boolean = true,
)

fun TrickyCard.toUi(): TrickyCardUiEntity {
    val (@DrawableRes imageRes: Int, @StringRes description: Int) =
        when (this) {
            is Freezing -> R.drawable.freeze to R.string.tricky_card_freezing
            is Tornado -> R.drawable.tornado to R.string.tricky_card_tornado
            is Harmony -> R.drawable.harmony to  R.string.tricky_card_harmony
        }
    return TrickyCardUiEntity(
        imageRes = imageRes,
        imageDescription = description,
        card = this,
    )
}