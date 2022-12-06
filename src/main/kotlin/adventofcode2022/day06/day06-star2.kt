package adventofcode2022.day01

import java.io.File

fun main(vararg args: String) {
    val input = File("data").readLines().first()

    val res = input.indices.first { i ->
        input.drop(i).take(14).toSet().size == 14
    }

    println(res + 14)
}
