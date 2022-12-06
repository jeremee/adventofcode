package adventofcode2022.day01

import java.io.File

fun main(vararg args: String) {
    val inputs = File("data").readLines()
        .map(String::toIntOrNull)
        .iterator()

    val sums = mutableListOf<Int>()
    var sum = 0

    while (inputs.hasNext()) {
        val input = inputs.next()
        if (input == null) {
            sums += sum
            sum = 0
        } else sum += input
    }
    sums += sum

    println(sums.sortedDescending().take(3).sum())
}