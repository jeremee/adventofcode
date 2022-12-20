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

    println("visibleTreesFromNorthCounts")
    val visibleTreesFromNorthCounts = (0 until width).map { x ->
        var maxSize = trees[0][x]

        (0 until height).map { y ->
            if (y == 0) true else {
                if (trees[y][x] > maxSize) {
                    maxSize = trees[y][x]
                    true
                } else false
            }
        }
            .also { println(it) }
    }

    println("visibleTreesFromSouthCounts")
    val visibleTreesFromSouthCounts = (0 until width).map { x ->
        var maxSize = trees[height - 1][x]

        (0 until height).reversed().map { y ->
            if (y == height-1) true else {
                if (trees[y][x] > maxSize) {
                    maxSize = trees[y][x]
                    true
                } else false
            }
        }.reversed()
            .also { println(it) }
    }

    println("visibleTreesFromWestCounts")
    val visibleTreesFromWestCounts = (0 until height).map { y ->
        var maxSize = trees[y][0]

        (0 until width).map { x ->
            if (x == 0) true else {
                if (trees[y][x] > maxSize) {
                    maxSize = trees[y][x]
                    true
                } else false
            }
        }
            .also { println(it) }
    }

    println("visibleTreesFromEastCounts")
    val visibleTreesFromEastCounts = (0 until height).map { y ->
        var maxSize = trees[y][width - 1]

        (0 until width).reversed().map { x ->
            if (x == width - 1) true else {
                if (trees[y][x] > maxSize) {
                    maxSize = trees[y][x]
                    true
                } else false
            }
        }.reversed()
            .also { println(it) }
    }

    val count = (0 until height).map { y ->
        (0 until width).map { x ->
            visibleTreesFromWestCounts[x][y] ||
                    visibleTreesFromEastCounts[x][y] ||
                    visibleTreesFromNorthCounts[y][x] ||
                    visibleTreesFromSouthCounts[y][x]
        }
            .count { it }
    }.sum()

    println(count)
}
