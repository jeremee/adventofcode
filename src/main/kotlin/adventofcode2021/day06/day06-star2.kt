package adventofcode2021.day06

import java.io.File

fun main(vararg args: String) {
    val fishList = File("data").readLines()
            .first()
            .split(",")
            .map(String::toInt)
            .toMutableList()

    var fishCountByAge = mapOf(
            0 to fishList.count { it == 0 }.toLong(),
            1 to fishList.count { it == 1 }.toLong(),
            2 to fishList.count { it == 2 }.toLong(),
            3 to fishList.count { it == 3 }.toLong(),
            4 to fishList.count { it == 4 }.toLong(),
            5 to fishList.count { it == 5 }.toLong(),
            6 to fishList.count { it == 6 }.toLong(),
            7 to fishList.count { it == 7 }.toLong(),
            8 to fishList.count { it == 8 }.toLong())


    (0 until 256).forEach {
        fishCountByAge = grow1Day(fishCountByAge)
    }

    println(fishCountByAge.values.sum())
}

fun grow1Day(groupedFishes: Map<Int, Long>): Map<Int, Long> = mapOf(
        0 to groupedFishes[1]!!,
        1 to groupedFishes[2]!!,
        2 to groupedFishes[3]!!,
        3 to groupedFishes[4]!!,
        4 to groupedFishes[5]!!,
        5 to groupedFishes[6]!!,
        6 to groupedFishes[7]!! + groupedFishes[0]!!,
        7 to groupedFishes[8]!!,
        8 to groupedFishes[0]!!)