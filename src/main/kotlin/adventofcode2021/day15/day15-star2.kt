package adventofcode2021.day15

import java.io.File

fun main(vararg args: String) {
    val board = File("data").readLines()
            .map { line ->
                line.toCharArray()
                        .map(Char::toString)
                        .map(String::toInt)
            }

    val computedDistances = ComputedDistanceBoard(board.first().size * 5, board.size * 5)

    computedDistances.set(0, 0, 0)
    var pointsToCompute = findDirectNeighbors(0, 0, computedDistances.width, computedDistances.height)
    while (pointsToCompute.isNotEmpty()) {
        pointsToCompute = pointsToCompute.map { (x, y) ->
            computeNeighbors(x, y, board, computedDistances)
        }.flatten()
    }

    println(computedDistances.get(computedDistances.height - 1, computedDistances.width - 1))
}

private fun computeNeighbors(x: Int, y: Int, board: List<List<Int>>, computedDistances: ComputedDistanceBoard): List<Pair<Int, Int>> {
    val shortestPathTo = findDirectNeighbors(x, y, computedDistances.width, computedDistances.height)
            .map { (neibX, neibY) -> computedDistances.get(neibX, neibY) }
            .filterNotNull()
            .min()

    if (shortestPathTo != null) {
        val shortedDistance = shortestPathTo + getBoardValue(x, y, board)
        if (computedDistances.get(x, y) == null || computedDistances.get(x, y)!! > shortedDistance) {
            computedDistances.set(x, y, shortedDistance)
            return findDirectNeighbors(x, y, computedDistances.width, computedDistances.height)
                    .filter { (neibX, neibY) ->
                        computedDistances.get(neibX, neibY) == null || computedDistances.get(neibX, neibY)!! > shortedDistance
                    }
        }
    }

    return emptyList()
}

private fun getBoardValue(x: Int, y: Int, board: List<List<Int>>): Int {
    val boardValue = board[y % board.size][x % board.first().size] + y / board.size + x / board.first().size
    val correctedBoardValue = boardValue % 10

    return if (boardValue / 10 > 0) correctedBoardValue + 1 else correctedBoardValue
}

private fun findDirectNeighbors(x: Int, y: Int, sizeX: Int, sizeY: Int): List<Pair<Int, Int>> =
        listOf(
                x - 1 to y,
                x + 1 to y,
                x to y - 1,
                x to y + 1
        ).mapNotNull { (neibX, neibY) ->
            if (neibX in 0 until sizeX && neibY in 0 until sizeY) {
                neibX to neibY
            } else null
        }

class ComputedDistanceBoard(val width: Int, val height: Int) {

    private val grid = (0 until height).map { arrayOfNulls<Int>(width).toMutableList() }

    fun set(x: Int, y: Int, value: Int) {
        grid[y][x] = value
    }

    fun get(x: Int, y: Int): Int? = grid[y][x]
}