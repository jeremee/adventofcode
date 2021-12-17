package adventofcode2021.day17

import java.io.File

fun main(vararg args: String) {
    val input = File("data").readLines().first().removePrefix("target area: ")
    val (xRangeAsString, yRangeAsString) = input.split(", ")

    val area = Area(extractIntRange(xRangeAsString), extractIntRange(yRangeAsString))

    val potentialYVelocities = findCompatibleYVelocities(area.yRange)
    val potentialXVelocities = findCompatibleXVelocities(area.xRange)

    val potentialVelocities = potentialYVelocities.map { velY ->
        potentialXVelocities.map { velX ->
            velX to velY
        }
    }.flatten().distinct()

    val matchVelocities = potentialVelocities.filter { (velX, velY) ->
        willProbeReach(Probe(0, 0, velX, velY), area)
    }

    println(matchVelocities.size)
}

private fun willProbeReach(p: Probe, a: Area): Boolean {
    var nextP = p
    while (nextP.y > a.yRange.min()!!) {
        nextP = nextP.nextPosition()
        if (nextP.reached(a)) return true
    }
    return false
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

private fun findCompatibleXVelocities(xRange: IntRange): List<Int> =
        (1..xRange.last).filter { vel ->
            isXVelocityCapableToReachTarget(vel, xRange)
        }

private fun isXVelocityCapableToReachTarget(vel: Int, target: IntRange): Boolean {
    var pos = 0
    var velocity = vel
    while (pos < target.first && velocity != 0) {
        pos += velocity
        velocity--
    }
    return pos in target
}