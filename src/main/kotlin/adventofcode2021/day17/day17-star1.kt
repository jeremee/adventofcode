package adventofcode2021.day17

import java.io.File

fun main(vararg args: String) {
    val input = File("data").readLines().first().removePrefix("target area: ")
    val (_, yRangeAsString) = input.split(", ")

    val area = Area(Int.MIN_VALUE..Int.MAX_VALUE, extractIntRange(yRangeAsString))

    val potentialYVelocities = findCompatibleYVelocities(area.yRange)

    val yVelocity = potentialYVelocities.maxBy { y ->
        val p = Probe(0, 0, 0, y)
        getMaxYIfReached(p, area) ?: Int.MIN_VALUE
    }

    println("Reached ${getMaxYIfReached(Probe(0, 0, 0, yVelocity!!), area)} using initial velocity of $yVelocity")
}

private fun getMaxYIfReached(p: Probe, a: Area): Int? {
    var highestY = p.y
    var nextP = p
    while (nextP.y > a.yRange.min()!!) {
        nextP = nextP.nextPosition()
        if (highestY < nextP.y) highestY = nextP.y
        if (nextP.reached(a)) return highestY
    }
    return null
}

private fun extractIntRange(text: String) = text
        .drop(2)
        .split("..")
        .map(String::toInt)
        .let { (start, end) -> start..end }

private fun findCompatibleYVelocities(yRange: IntRange): List<Int> =
        (0..MAX_Y_VELOCITY_TO_SEARCH).filter { vel ->
            (0..MAX_DIFF_STEP_BETWEEN_UP_AND_DOWN).any { c ->
                (yRange).any { yReached ->
                    sumTo0(vel) == sumTo0(vel + c) + yReached
                }
            }
        } + listOf(yRange.first..0).flatten()

private fun sumTo0(y: Int) = ((y + 1) * y) / 2