package adventofcode2021.day07

import java.io.File
import kotlin.math.abs

fun main(vararg args: String) {
    val crabs = File("data").readLines().first().split(",").map(String::toInt)

    var minFuel = Int.MAX_VALUE

    for (i in (crabs.min()!!..crabs.max()!!)) {
        val fuel = neededFuelToGoTo(i, crabs)
        if (fuel < minFuel) minFuel = fuel
    }

    println(minFuel)
}

private fun neededFuelToGoTo(position: Int, crabs: List<Int>) =
        crabs.sumBy { abs(it - position) }