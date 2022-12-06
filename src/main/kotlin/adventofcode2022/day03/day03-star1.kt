package adventofcode2022.day01

import java.io.File

fun main(vararg args: String) {
    val lines = File("data").readLines()
    val alphabet = "abcdefghijklmnopqrstuvwxyz".let { it + it.toUpperCase() }

    val result = lines.map { line ->
        val rucksack1 = line.take(line.length / 2).toCharArray().toSet()
        val rucksack2 = line.drop(line.length / 2).toCharArray().toSet()

        val error = rucksack1.intersect(rucksack2).first()
        alphabet.indexOf(error) + 1
    }.sum()

    println(result)
}