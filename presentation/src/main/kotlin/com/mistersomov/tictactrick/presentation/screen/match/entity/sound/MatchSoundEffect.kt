package com.mistersomov.tictactrick.presentation.screen.match.entity.sound

import com.mistersomov.tictactrick.presentation.common.sound.SoundEffect
import com.mistersomov.tictactrick.presentation.R

enum class MatchSoundEffect(override val resId: Int) : SoundEffect {
    PREPARE(R.raw.prepare_match),
    MOVE(R.raw.move),
    VICTORY(R.raw.victory),
    DRAW(R.raw.draw),
    DEFEAT(R.raw.defeat),
    ADD_TRICKY_CARD(R.raw.add_card),
    ACTIVATE_TRICKY_CARD(R.raw.activate),
    REMOVE_TRICKY_CARD(R.raw.remove_card),
}