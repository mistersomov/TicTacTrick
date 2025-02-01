package com.mistersomov.domain.entity

import com.mistersomov.domain.entity.CellType.EMPTY

data class Cell(
    val id: Int,
    val type: CellType = EMPTY,
)