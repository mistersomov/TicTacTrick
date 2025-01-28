package com.mistersomov.domain.model

import com.mistersomov.domain.model.CellType.EMPTY

data class Cell(
    val id: Int,
    val type: CellType = EMPTY,
)