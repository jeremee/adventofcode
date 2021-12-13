package adventofcode2021.day10

import java.io.File

fun main(vararg args: String) {
    val lines = File("data").readLines()
            .map(String::toList)

    val scores = lines.map {
        val unlcosedChar = getUnclosedChars(it)
        var score = 0L
        unlcosedChar.forEach {
            score *= 5
            score += when (it) {
                ')' -> 1
                ']' -> 2
                '}' -> 3
                '>' -> 4
                else -> throw IllegalStateException("Unhandled char $it")
            }
        }
        score
    }.filter { it != 0L }

    println(scores.sorted()
            .get(scores.size / 2))
}

@OptIn(ExperimentalStdlibApi::class)
private fun getUnclosedChars(line: List<Char>): List<Char> {
    val openedChar = mutableListOf<Char>()

    line.forEach {
        when (it) {
            '(', '[', '{', '<' -> openedChar.add(it)
            ')' -> if (openedChar.last() == '(') openedChar.removeLast() else return emptyList()
            ']' -> if (openedChar.last() == '[') openedChar.removeLast() else return emptyList()
            '}' -> if (openedChar.last() == '{') openedChar.removeLast() else return emptyList()
            '>' -> if (openedChar.last() == '<') openedChar.removeLast() else return emptyList()
            else -> throw IllegalStateException("Unhandled char $it")
        }
    }

    return openedChar.reversed()
            .map {
                when (it) {
                    '(' -> ')'
                    '[' -> ']'
                    '{' -> '}'
                    '<' -> '>'
                    else -> throw IllegalStateException("Unhandled char $it")
                }
            }
}