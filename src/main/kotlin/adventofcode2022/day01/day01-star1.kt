package adventofcode2022.day01

import java.io.File

fun main(vararg args: String) {
    val inputs = File("data").readLines()
        .map(String::toIntOrNull)
        .iterator()

    var max = 0
    var sum = 0

    while (inputs.hasNext()) {
        val input = inputs.next()
        if (input == null) sum = 0
        else sum += input
        max = maxOf(max, sum)
    }

    println(max)
}