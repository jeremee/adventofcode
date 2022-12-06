package adventofcode2022.day01

import java.io.File

fun main(vararg args: String) {
    val lines = File("data").readLines()
    val res = lines
        .map { input ->
            input.split(",").let {
                val firstGroup = it.first().split("-").map(String::toInt).let { it.first()..it.last() }.toSet()
                val secondGroup = it.last().split("-").map(String::toInt).let { it.first()..it.last() }.toSet()
                firstGroup to secondGroup
            }
        }
        .count { (firstGroup, secondGroup) -> firstGroup.any { secondGroup.contains(it) } || secondGroup.any { firstGroup.contains(it) } }

    println(res)
}
