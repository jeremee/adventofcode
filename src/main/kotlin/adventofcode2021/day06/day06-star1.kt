package adventofcode2021.day06

import java.io.File

fun main(vararg args: String) {
    val fishes = File("data").readLines()
            .first()
            .split(",")
            .map(String::toInt)
            .toMutableList()

    (0 until 80).forEach {
        grow1Day(fishes)
    }

    println(fishes.size)
}

fun grow1Day(fishes: MutableList<Int>) {
    val size = fishes.size
    (0 until size).forEach { i ->
        if (fishes[i] == 0) {
            fishes[i] = 6
            fishes.add(8)
        } else {
            fishes[i] -= 1
        }
    }
}