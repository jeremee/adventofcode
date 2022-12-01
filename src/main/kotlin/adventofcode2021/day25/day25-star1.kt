package adventofcode2021.day25

import adventofcode2021.day25.CucumberType.EAST
import adventofcode2021.day25.CucumberType.SOUTH
import java.io.File

fun main(vararg args: String) {
    val board = File("data").readLines().createBoard()

    var i = 0
    var moved = Int.MAX_VALUE
    while (moved != 0) {
    //    board.print()
        val cEast = board.moveCucumbers(EAST)
        val cSouth = board.moveCucumbers(SOUTH)
        moved = cEast.size + cSouth.size
        i++
    }

   // board.print()

    println(i)
}

private fun List<String>.createBoard(): Board {
    val cucumbers = this.mapIndexed { y, line ->
        line.mapIndexedNotNull { x, value ->
            when (value) {
                'v' -> Cucumber(x, y, SOUTH)
                '>' -> Cucumber(x, y, EAST)
                else -> null
            }
        }
    }.flatten()

    return Board(cucumbers, this[0].length, this.size)
}

data class Board(val cucumbers: List<Cucumber>, val xMax: Int, val yMax: Int) {

    private val cucumbersSouth = cucumbers.filter { it.type == SOUTH }
    private val cucumbersEast = cucumbers.filter { it.type == EAST }

    fun moveCucumbers(type: CucumberType) = when (type) {
        EAST -> {
            cucumbersEast.filterMovableCucumbers().moveCucumbers()
        }
        SOUTH -> {
            cucumbersSouth.filterMovableCucumbers().moveCucumbers()
        }
    }

    private fun List<Cucumber>.moveCucumbers(): List<Cucumber> {
        this.forEach { c ->
            c.nextPos(xMax, yMax).let { (newX, newY) ->
                c.x = newX
                c.y = newY
            }
        }
        return this
    }

    fun List<Cucumber>.filterMovableCucumbers(): List<Cucumber> =
            this.filter {
                val newPos = it.nextPos(xMax, yMax)
                cucumbers.none {
                    it.x == newPos.first && it.y == newPos.second
                }
            }


    fun print() {
        (0 until yMax).forEach { y ->
            println((0 until xMax).map { x ->
                cucumbers.firstOrNull { it.x == x && it.y == y }?.let {
                    when (it.type) {
                        EAST -> '>'
                        SOUTH -> 'v'
                    }
                } ?: '.'
            }.joinToString(""))
        }
        println()
    }
}

data class Cucumber(var x: Int, var y: Int, val type: CucumberType) {
    fun nextPos(xMax: Int, yMax: Int) = when (type) {
        EAST -> Pair((x + 1) % xMax, y)
        SOUTH -> Pair(x, (y + 1) % yMax)
    }
}

enum class CucumberType {
    EAST, SOUTH;
}