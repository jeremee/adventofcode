package adventofcode2021.day01

import java.io.File

fun main(vararg args: String) {
    val inputs = File("data").readLines().map(String::toInt)

    val increase = inputs
            .windowed(2)
            .filter { it[0] < it[1] }
            .count()

    println(increase)
}