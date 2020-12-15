import Instruction1.Action.*
import java.io.File
import kotlin.math.abs

fun main(vararg args: String) {
    val inputs = File(args[0]).readLines()

    var ship = Ship1(0, 0, 0)
    val instructions = inputs.map(::Instruction1)

    instructions.forEach { ship = it.applyToShip(ship) }

    println(abs(ship.x) + abs(ship.y))
}

private data class Instruction1 private constructor(val action: Action, val value: Int) {

    constructor(text: String) : this(Action.valueOf(text.first().toString()), text.drop(1).toInt())

    enum class Action {
        N, S, E, W, L, R, F;
    }

    fun applyToShip(ship: Ship1): Ship1 = when (action) {
        N -> ship.copy(y = ship.y + value)
        S -> ship.copy(y = ship.y - value)
        E -> ship.copy(x = ship.x + value)
        W -> ship.copy(x = ship.x - value)
        L -> ship.copy(degres = ((ship.degres - value) % 360).let {
            if (it < 0) it + 360 else it
        })
        R -> ship.copy(degres = (ship.degres + value) % 360)
        F -> when (ship.degres) {
            0 -> ship.copy(x = ship.x + value)
            90 -> ship.copy(y = ship.y - value)
            180 -> ship.copy(x = ship.x - value)
            270 -> ship.copy(y = ship.y + value)
            else -> throw IllegalStateException("Unhandled degree ${ship.degres}")
        }
    }
}

data class Ship1(val x: Int, val y: Int, val degres: Int)