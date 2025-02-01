package com.mistersomov.happyandhealth.domain.entity

import com.mistersomov.happyandhealth.domain.entity.CellType.EMPTY

data class Cell(
    val id: Int,
    val type: CellType = EMPTY,
)