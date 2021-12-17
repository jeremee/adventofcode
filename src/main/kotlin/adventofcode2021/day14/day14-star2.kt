package adventofcode2021.day14

import java.io.File

fun main(vararg args: String) {
    val inputs = File("data").readLines()
    val polymerAsChars = inputs[0].toCharArray().toList()
    val polymerList = polymerAsChars.windowed(2)
            .map { it.joinToString("") }
    var countByPolymer = polymerList.distinct()
            .map { polymer -> polymer to polymerList.count { it == polymer }.toLong() }
            .toMap()

    val mapping = inputs.drop(2)
            .map {
                val (pair, inserted) = it.split(" -> ")
                pair to listOf(pair[0] + inserted, inserted + pair[1])
            }.toMap()


    (1..40).forEach {
        countByPolymer = insertion(countByPolymer, mapping)
    }

    val countByChar = mutableMapOf<Char, Long>()
    countByPolymer.forEach { polymer, count ->
        polymer.toCharArray().forEach { char -> countByChar.addToMap(char, count) }
    }
    countByChar[polymerAsChars.first()] = countByChar[polymerAsChars.first()]!! + 1
    countByChar[polymerAsChars.last()] = countByChar[polymerAsChars.last()]!! + 1

    println(countByChar.values.let { it.max()!! - it.min()!! } / 2)
}

private fun insertion(countByPolymer: Map<String, Long>, mapping: Map<String, List<String>>): Map<String, Long> {
    val newCountByPolymer = mutableMapOf<String, Long>()
    countByPolymer.forEach { polymer, count ->
        mapping[polymer]!!.forEach { newPolymer -> newCountByPolymer.addToMap(newPolymer, count) }
    }
    return newCountByPolymer
}

private fun <T> MutableMap<T, Long>.addToMap(key: T, count: Long) {
    set(key, (get(key) ?: 0) + count)
}