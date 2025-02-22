package com.mistersomov.tictactrick.domain.use_case

import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.entity.tricky_card.TrickyCard

interface ApplyTrickyCardUseCase {
    operator fun invoke(cells: List<Cell>, card: TrickyCard): List<Cell>
}