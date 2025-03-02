package com.mistersomov.tictactrick.domain.entity.tricky_card

sealed interface TrickyCard {

    sealed interface Selectable : TrickyCard {
        val sourceId: Int

        sealed interface SingleSelectable : Selectable {
            data class Freezing(override val sourceId: Int = -1) : SingleSelectable
            data class Blaze(override val sourceId: Int = -1) : SingleSelectable
        }

        sealed interface DualSelectable : Selectable {
            data class Tornado(
                override val sourceId: Int = -1,
                val targetId: Int = -1,
            ) : DualSelectable
        }
    }

    sealed interface Global : TrickyCard {
        data object Harmony : Global
    }

}