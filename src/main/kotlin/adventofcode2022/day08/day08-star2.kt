package adventofcode2022.day08

import java.io.File

fun main(vararg args: String) {
    val trees = File("data").readLines()
        .map { line ->
            line.map {
                it.toString().toInt()
            }
        }

    val height = trees.size
    val width = trees.first().size

    var max = 0

    (1 until width - 1).forEach { x ->
        (1 until height - 1).forEach { y ->
            val size = trees[y][x]

            val toWest = (0..x - 1).reversed().takeWhile { i -> trees[y][i] < size }.let {
                val toEdge = it.contains(0)
                it.count() + if (toEdge) 0 else 1
            }
            val toEast = (x + 1..width - 1).takeWhile { i -> trees[y][i] < size }.let {
                val toEdge = it.contains(width - 1)
                it.count() + if (toEdge) 0 else 1
            }
            val toNorth = (0..y - 1).reversed().takeWhile { i -> trees[i][x] < size }.let {
                val toEdge = it.contains(0)
                it.count() + if (toEdge) 0 else 1
            }
            val toSouth = (y + 1..height - 1).takeWhile { i -> trees[i][x] < size }.let {
                val toEdge = it.contains(height - 1)
                it.count() + if (toEdge) 0 else 1
            }

            val scenic = toEast*toWest*toNorth*toSouth
            if (scenic > max) max = scenic
        }
    }

    println(max)
}
