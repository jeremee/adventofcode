package adventofcode2021.day02

import java.io.File

fun main(vararg args: String) {
    val commands = File(args[0]).readLines()
            .map { it.split(" ") }
            .map { Command(it.first(), it.last().toInt()) }

    var aim = 0
    var x = 0
    var depth = 0
    commands.forEach {
        when(it.direction) {
            Command.Direction.up -> aim -= it.value
            Command.Direction.down -> aim += it.value
            Command.Direction.forward -> {
                x += it.value
                depth += it.value * aim
            }
        }
    }

    println(x * depth)
}