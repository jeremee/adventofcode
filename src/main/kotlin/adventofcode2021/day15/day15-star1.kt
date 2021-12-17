package adventofcode2021.day15

import java.io.File

fun main(vararg args: String) {
    val board = File("data").readLines()
            .map { line ->
                line.toCharArray()
                        .map(Char::toString)
                        .map(String::toInt)
            }

    val computedDistances = board.indices.map { arrayOfNulls<Int>(board.first().size).toMutableList() }
    computedDistances[0][0] = 0

    var toCompute = findUncomputedNeighboors(computedDistances)
    while (toCompute.isNotEmpty()) {
        toCompute.forEach { (x, y) ->
            val shortestDistance = findDirectNeighbors(x, y, computedDistances.first().size, computedDistances.size)
                    .map { (neibX, neibY) -> computedDistances[neibY][neibX] }
                    .filterNotNull()
                    .min()

            if (shortestDistance != null) computedDistances[y][x] = shortestDistance + board[y][x]
        }
        toCompute = findUncomputedNeighboors(computedDistances)
    }

    println(computedDistances.last().last())
}

private fun findUncomputedNeighboors(computedDistances: List<List<Int?>>): Set<Pair<Int, Int>> =
        computedDistances.mapIndexed { y, line ->
            line.mapIndexedNotNull { x, value ->
                val distance = computedDistances[y][x]
                if (distance != null) {
                    findDirectNeighbors(x, y, computedDistances.first().size, computedDistances.size)
                            .map { (neighborX, neighborY) ->
                                if (computedDistances[neighborY][neighborX] == null) neighborX to neighborY
                                else null
                            }
                } else null
            }.flatten()
        }.flatten().filterNotNull().toSet()


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