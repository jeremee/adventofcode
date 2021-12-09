package adventofcode2021.day09

import java.io.File

fun main(vararg args: String) {
    val inputs = File(args[0]).readLines()
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

    val orderedBasinSizes = lowestPoints.map { (x, y) -> getBasin(x, y, inputs) }
            .map(List<*>::size)
            .sortedByDescending { it }
            .take(3)

    println(orderedBasinSizes[0] * orderedBasinSizes[1] * orderedBasinSizes[2])
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

fun getBasin(x: Int, y: Int, heights: List<List<Int>>): List<Pair<Int, Int>> {
    val basinPoints = mutableListOf<Pair<Int, Int>>()
    expandBasinRec(x, y, heights, basinPoints)
    return basinPoints
}

fun expandBasinRec(x: Int, y: Int, heights: List<List<Int>>, basinPoints: MutableList<Pair<Int, Int>>) {
    if (basinPoints.contains(x to y)) return
    else basinPoints.add(x to y)
    val yMax = heights.size - 1
    val xMax = heights.first().size - 1

    if (x > 0 && heights[y][x - 1] < 9) expandBasinRec(x - 1, y, heights, basinPoints)
    if (x < xMax && heights[y][x + 1] < 9) expandBasinRec(x + 1, y, heights, basinPoints)
    if (y > 0 && heights[y - 1][x] < 9) expandBasinRec(x, y - 1, heights, basinPoints)
    if (y < yMax && heights[y + 1][x] < 9) expandBasinRec(x, y + 1, heights, basinPoints)
}
