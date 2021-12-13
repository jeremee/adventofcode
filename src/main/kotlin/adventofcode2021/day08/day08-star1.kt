package adventofcode2021.day08

import java.io.File

fun main(vararg args: String) {
    val inputs = File("data").readLines()

    println(
            inputs.map {
                it.split(" | ").last()
                        .split(" ")
                        .count { it.length in listOf(2, 3, 4, 7) }
            }.sum()
    )
}