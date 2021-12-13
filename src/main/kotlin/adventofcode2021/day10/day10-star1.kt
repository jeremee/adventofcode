package adventofcode2021.day10

import java.io.File

fun main(vararg args: String) {
    val lines = File("data").readLines()
            .map(String::toList)

    println(lines.map {
        val corruptedChar = corruptedChar(it)
        when (corruptedChar) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            null -> 0
            else -> throw IllegalStateException("Unhandled char $corruptedChar")
        }
    }.sum())
}

@OptIn(ExperimentalStdlibApi::class)
private fun corruptedChar(line: List<Char>): Char? {
    val openedChar = mutableListOf<Char>()

    line.forEach {
        when (it) {
            '(', '[', '{', '<' -> openedChar.add(it)
            ')' -> if (openedChar.last() == '(') openedChar.removeLast() else return it
            ']' -> if (openedChar.last() == '[') openedChar.removeLast() else return it
            '}' -> if (openedChar.last() == '{') openedChar.removeLast() else return it
            '>' -> if (openedChar.last() == '<') openedChar.removeLast() else return it
            else -> throw IllegalStateException("Unhandled char $it")
        }
    }
    return null
}