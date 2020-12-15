import Instruction2.Action.*
import java.io.File
import kotlin.math.abs

fun main(vararg args: String) {
    val inputs = File(args[0]).readLines()

    var ship = Ship2(0, 0, 10, 1)
    val instructions = inputs.map(::Instruction2)

    instructions.forEach { ship = it.applyToShip(ship) }

    println(abs(ship.x) + abs(ship.y))
}

private data class Instruction2 private constructor(val action: Action, val value: Int) {

    constructor(text: String) : this(Action.valueOf(text.first().toString()), text.drop(1).toInt())

    enum class Action {
        N, S, E, W, L, R, F;
    }

    fun applyToShip(ship: Ship2) = when (action) {
        N -> ship.copy(yWayPoint = ship.yWayPoint + value)
        S -> ship.copy(yWayPoint = ship.yWayPoint - value)
        E -> ship.copy(xWayPoint = ship.xWayPoint + value)
        W -> ship.copy(xWayPoint = ship.xWayPoint - value)
        L -> rotateLeft(ship, value / 90)
        R -> rotateRight(ship, value / 90)
        F -> ship.copy(x = ship.x + ship.xWayPoint * value, y = ship.y + ship.yWayPoint * value)
    }

    private tailrec fun rotateLeft(ship: Ship2, times: Int) : Ship2 =
        if (times == 1) rotateLeft(ship) else rotateLeft(rotateLeft(ship), times-1)
    private fun rotateLeft(ship: Ship2) = ship.copy(xWayPoint = -ship.yWayPoint, yWayPoint = ship.xWayPoint)
    private tailrec fun rotateRight(ship: Ship2, times: Int) : Ship2 =
        if (times == 1) rotateRight(ship) else rotateRight(rotateRight(ship), times-1)
    private fun rotateRight(ship: Ship2) = ship.copy(xWayPoint = ship.yWayPoint, yWayPoint = -ship.xWayPoint)
}

data class Ship2(val x: Int, val y: Int, val xWayPoint: Int, val yWayPoint: Int)