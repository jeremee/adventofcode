package adventofcode2021.day09

import java.io.File

fun main(vararg args: String) {
    val inputs = File("data").readLines()
            .map(String::toList)
            .map { it.map(Char::toString).map(String::toInt) }

    val lowestPoints = inputs.indices.map { y ->
        inputs.first().indices.map { x ->
            x to y
        }
    }.flatten()
            .filter { (x, y) ->
                isLowPoint(x, y, inputs)
            }

    println(lowestPoints.map { (x, y) -> inputs[y][x] + 1 }.sum())
}

private fun isLowPoint(x: Int, y: Int, heights: List<List<Int>>): Boolean {
    val yMax = heights.size - 1
    val xMax = heights.first().size - 1
    if (x > 0 && heights[y][x - 1] <= heights[y][x]) return false
    if (x < xMax && heights[y][x + 1] <= heights[y][x]) return false
    if (y > 0 && heights[y - 1][x] <= heights[y][x]) return false
    if (y < yMax && heights[y + 1][x] <= heights[y][x]) return false
    return true
}