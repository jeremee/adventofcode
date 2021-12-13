package adventofcode2021.day11

import java.io.File

fun main(vararg args: String) {
    val octopuses = File("data").readLines()
            .map { it.toCharArray() }
            .map { it.map(Char::toString).map(String::toInt).toMutableList() }

    var nbFlash = 0
    var step = 0
    while (nbFlash != 100) {
        nbFlash = nextStep(octopuses)
        step++
    }

    println(step)
}

private fun nextStep(octopuses: List<MutableList<Int>>): Int {
    val flashedToProcessed = mutableSetOf<Pair<Int, Int>>()
    (0..9).forEach { x ->
        (0..9).forEach { y ->
            increaseEnergy(x, y, octopuses)
            if (octopuses[y][x] == 0) flashedToProcessed.add(x to y)
        }
    }

    val flashed = mutableSetOf<Pair<Int, Int>>()

    while (!flashed.containsAll(flashedToProcessed)) {
        val flash = flashedToProcessed.minus(flashed).first()
        val (x, y) = flash
        (-1..1).forEach { xDiff ->
            (-1..1).forEach { yDiff ->
                val xToEnergize = x + xDiff
                val yToEnergize = y + yDiff
                if (xToEnergize in 0..9 && yToEnergize in 0..9) {
                    if (!flashedToProcessed.contains(xToEnergize to yToEnergize)) {
                        increaseEnergy(xToEnergize, yToEnergize, octopuses)
                        if (octopuses[yToEnergize][xToEnergize] == 0) flashedToProcessed.add(xToEnergize to yToEnergize)
                    }
                }
            }
        }
        flashed.add(flash)
    }

    return flashed.size
}

private fun increaseEnergy(x: Int, y: Int, octopuses: List<MutableList<Int>>) {
    octopuses[y][x] = (octopuses[y][x] + 1) % 10
}

private fun findNextFlash(octopuses: List<List<Int>>, alreadyFlashed: List<Pair<Int, Int>>): Pair<Int, Int>? {
    (0..9).forEach { x ->
        (0..9).forEach { y ->
            if (octopuses[y][x] == 0 && !alreadyFlashed.contains(x to y)) return x to y
        }
    }
    return null
}