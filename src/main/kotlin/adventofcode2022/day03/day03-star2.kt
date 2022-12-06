package adventofcode2022.day01

import java.io.File

fun main(vararg args: String) {
    val lines = File("data").readLines()
    val alphabet = "abcdefghijklmnopqrstuvwxyz".let { it + it.toUpperCase() }

    val result = (lines.indices step 3).map { i ->
        val rucksack1 = lines[i].toCharArray().toSet()
        val rucksack2 = lines[i + 1].toCharArray().toSet()
        val rucksack3 = lines[i + 2].toCharArray().toSet()
        rucksack1.intersect(rucksack2.intersect(rucksack3)).first()
    }.map { badge ->
        alphabet.indexOf(badge) + 1
    }.sum()

    println(result)
}