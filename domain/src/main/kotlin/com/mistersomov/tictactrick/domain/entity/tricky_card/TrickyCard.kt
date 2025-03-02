package com.mistersomov.tictactrick.domain.entity.tricky_card

sealed interface TrickyCard {

    sealed interface Selectable : TrickyCard {
        val sourceId: Int

        data class Freezing(override val sourceId: Int = -1) : Selectable
        data class Tornado(
            override val sourceId: Int = -1,
            val targetId: Int = -1,
        ) : Selectable
    }

    sealed interface Global : TrickyCard {
        data object Harmony : Global
    }

}