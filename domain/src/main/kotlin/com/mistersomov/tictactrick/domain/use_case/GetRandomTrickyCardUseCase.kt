package com.mistersomov.tictactrick.domain.use_case

import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard

interface GetRandomTrickyCardUseCase {
    operator fun invoke(): List<TrickyCard>
}