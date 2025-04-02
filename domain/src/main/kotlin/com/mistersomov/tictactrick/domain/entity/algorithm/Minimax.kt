package com.mistersomov.tictactrick.domain.entity.algorithm

import com.mistersomov.tictactrick.domain.entity.MatchStatus.Draw
import com.mistersomov.tictactrick.domain.entity.MatchStatus.Victory
import com.mistersomov.tictactrick.domain.entity.ai.AiMoveEntity
import com.mistersomov.tictactrick.domain.entity.board.BoardMode
import com.mistersomov.tictactrick.domain.entity.board.Cell
import com.mistersomov.tictactrick.domain.entity.board.CellType
import com.mistersomov.tictactrick.domain.use_case.GetMatchStatusUseCase
import javax.inject.Inject

class Minimax @Inject constructor(
    private val getMatchStatusUseCase: GetMatchStatusUseCase,
) : Algorithm {

    private companion object {
        const val UNKNOWN_INDEX = -1
        const val DEPTH = 5
        const val WIN_POINTS = 10
        const val LOOSE_POINTS = -10
        const val DRAW_POINTS = 0
        const val PROBABILITY_FOR_RANDOM = 20
    }

    override fun getBestMove(
        cells: List<Cell>,
        boardMode: BoardMode,
        isCrossMove: Boolean,
    ): AiMoveEntity =
        if ((0..100).random() <= PROBABILITY_FOR_RANDOM) {
            getRandomMove(cells)
        } else {
            minimax(
                cells = cells,
                boardMode = boardMode,
                depth = DEPTH,
                maximize = isCrossMove,
                alpha = Int.MIN_VALUE,
                beta = Int.MAX_VALUE,
            )
        }

    private fun minimax(
        cells: List<Cell>,
        boardMode: BoardMode,
        depth: Int,
        maximize: Boolean,
        alpha: Int,
        beta: Int
    ): AiMoveEntity {
        val status = getMatchStatusUseCase(cells, boardMode)

        when {
            status is Victory -> {
                val score = if (status.winner == CellType.CROSS) WIN_POINTS else LOOSE_POINTS
                return AiMoveEntity(UNKNOWN_INDEX, score - depth)
            }
            status is Draw -> return AiMoveEntity(UNKNOWN_INDEX, DRAW_POINTS)
            depth == 0 -> return AiMoveEntity(
                cells.indexOfFirst { it.type == CellType.EMPTY },
                calculateTypeDiff(cells)
            )
        }

        var bestMove = AiMoveEntity(
            cells.indexOfFirst { it.type == CellType.EMPTY },
            if (maximize) Int.MIN_VALUE else Int.MAX_VALUE,
        )
        var alphaTmp = alpha
        var betaTmp = beta

        for (i in cells.indices) {
            if (cells[i].type == CellType.EMPTY) {
                val updatedCells = cells.toMutableList()
                updatedCells[i] = updatedCells[i].copy(type = if (maximize) CellType.CROSS else CellType.ZERO)

                val move = minimax(updatedCells, boardMode, depth - 1, !maximize, alphaTmp, betaTmp)

                if (maximize) {
                    if (move.score > bestMove.score) {
                        bestMove = AiMoveEntity(i, move.score)
                    }
                    alphaTmp = maxOf(alphaTmp, move.score)
                } else {
                    if (move.score < bestMove.score) {
                        bestMove = AiMoveEntity(i, move.score)
                    }
                    betaTmp = minOf(betaTmp, move.score)
                }
                if (betaTmp <= alphaTmp) break
            }
        }

        return bestMove
    }

    private fun getRandomMove(cells: List<Cell>): AiMoveEntity {
        val availableMoves = cells.mapIndexedNotNull { index, cell ->
            if (cell.type == CellType.EMPTY) index else null
        }
        return if (availableMoves.isNotEmpty()) {
            AiMoveEntity(availableMoves.random(), 0)
        } else {
            AiMoveEntity(UNKNOWN_INDEX, DRAW_POINTS)
        }
    }

    private fun calculateTypeDiff(cells: List<Cell>): Int =
        cells.count { it.type == CellType.CROSS } - cells.count { it.type == CellType.ZERO }

}