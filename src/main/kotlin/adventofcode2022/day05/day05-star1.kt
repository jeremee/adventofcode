package adventofcode2022.day05

import java.io.File

fun main(vararg args: String) {
    val input = File("data").readLines()

    val stackInput = input.take(input.indexOf("") - 1)
    val movementsInput = input.drop(input.indexOf("") + 1)

    val stack = (1..9).map { mutableListOf<Char>() }
    stackInput.forEach { input ->
        input.mapIndexedNotNull { i, c -> if (c == '[') i else null }
            .forEach { i ->
                val char = input[i + 1]
                val columnIndex = i / 4
                stack[columnIndex].add(char)
            }
    }

    movementsInput.forEach { input ->
        val splitInput = input.split(" ")
        val numberToMove = splitInput[1].toInt()
        val from = splitInput[3].toInt() - 1
        val to = splitInput[5].toInt() - 1

        (1..numberToMove).forEach {
            stack[to].add(0, stack[from].first())
            stack[from].removeAt(0)
        }
    }

    println(stack.mapNotNull(List<Char>::firstOrNull).joinToString(""))
}
