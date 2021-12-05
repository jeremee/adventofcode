package adventofcode2021.day02

import java.io.File

fun main(vararg args: String) {
    val commands = File(args[0]).readLines()
            .map { it.split(" ") }
            .map { Command(it.first(), it.last().toInt()) }

    var pos = Pos(0, 0)
    commands.forEach { pos = it.move(pos) }

    println(pos.x * pos.depth)
}

private fun Command.move(pos: Pos) = when (direction) {
    Command.Direction.down -> Pos(pos.x, pos.depth + value)
    Command.Direction.up -> Pos(pos.x, pos.depth - value)
    Command.Direction.forward -> Pos(pos.x + value, pos.depth)
}

private data class Pos(val x: Int, val depth: Int)