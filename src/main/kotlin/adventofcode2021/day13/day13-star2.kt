package adventofcode2021.day13

import java.io.File

fun main(vararg args: String) {
    val inputs = File("data").readLines()
    val endSectionIndex = inputs.indexOf("")

    var dots = inputs.take(endSectionIndex)
            .map {
                val (a, b) = it.split(",")
                a.toInt() to b.toInt()
            }

    inputs.drop(endSectionIndex + 1)
            .forEach {
                val (axis, positionAsString) = it.removePrefix("fold along ")
                        .split("=")
                dots = fold(axis == "x", positionAsString.toInt(), dots)
            }

    val xMax = dots.map(Pair<Int, *>::first).max()!!
    val yMax = dots.map(Pair<*, Int>::second).max()!!

    (0..yMax).forEach {y ->
        (0..xMax).forEach {x ->
            print(if (dots.contains(x to y)) "#" else ".")
        }
        println()
    }
}

private fun fold(isX: Boolean, position: Int, dots: List<Pair<Int, Int>>): List<Pair<Int, Int>> =
        dots.map { (x, y) ->
            if (isX && x > position) {
                val newX = (2 * position) - x
                newX to y
            } else if (!isX && y > position) {
                val newY = (2 * position) - y
                x to newY
            } else x to y
        }.distinct()