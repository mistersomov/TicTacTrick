package com.mistersomov.tictactrick.domain.use_case

import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard

internal class GetRandomTrickyCardUseCaseImpl(
    private val trickyCards: List<TrickyCard>,
) : GetRandomTrickyCardUseCase {

    private companion object {
        const val MAX_COUNT_PER_PLAYER = 2
    }

    override operator fun invoke(): List<TrickyCard> =
        trickyCards.shuffled().take(MAX_COUNT_PER_PLAYER)

}