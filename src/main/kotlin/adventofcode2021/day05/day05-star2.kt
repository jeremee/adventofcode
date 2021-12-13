package adventofcode2021.day05

import java.io.File

fun main(vararg args: String) {
    val inputs = File("data").readLines()

    val ventLines = inputs.map {
        val (p1, p2) = it.split(" -> ").map {
            val (x, y) = it.split(",").map(String::toInt)
            Pos(x, y)
        }
        VentLineWithDiag(p1, p2)
    }

    val countByPos = mutableMapOf<Pos, Int>()
    ventLines.forEach {
        it.getPos().forEach {
            if (countByPos.contains(it)) countByPos[it] = countByPos[it]!! + 1
            else countByPos[it] = 1
        }
    }

    println(countByPos.filter { it.value > 1 }.size)
}