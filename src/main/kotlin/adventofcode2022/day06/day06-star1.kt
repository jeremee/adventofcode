package adventofcode2022.day01

import java.io.File

fun main(vararg args: String) {
    val input = File("testdata").readLines().first()

    val res = input.indices.first { i ->
        input.drop(i).take(4).toSet().size == 4
    }

    println(res + 4)
}
