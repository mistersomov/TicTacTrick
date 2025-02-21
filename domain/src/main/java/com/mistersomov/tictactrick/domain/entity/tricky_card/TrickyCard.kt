package com.mistersomov.tictactrick.domain.entity.tricky_card

sealed interface TrickyCard {
    class Freezing(val cellId: Int? = null) : TrickyCard
    data object Tornado : TrickyCard
}