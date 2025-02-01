package com.mistersomov.happyandhealth.domain.use_case

import com.mistersomov.happyandhealth.domain.entity.Cell
import com.mistersomov.happyandhealth.domain.entity.GameStatus

interface GetGameStatusUseCase {
    operator fun invoke(cells: List<Cell>, isCrossMove: Boolean): GameStatus
}