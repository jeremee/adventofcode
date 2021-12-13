package adventofcode2021.day04

import java.io.File

fun main(vararg args: String) {
    val lines = File("data").readLines()

    val numbers = lines.first().split(",").map(String::toInt)

    val boardLines = lines
            .drop(1)
            .map {
                val splitStrings = it.split(" ").filter { it.isNotEmpty() }
                if (splitStrings.isEmpty()) emptyList<Int>()
                else splitStrings.map(String::toInt)
            }

    val boards = (boardLines.indices)
            .filter { boardLines[it].isEmpty() }
            .map { Board(boardLines.subList(it + 1, it + 6)) }

    (5..numbers.size).first {
        val calledNumbers = numbers.take(it)
        val board = boards.firstOrNull { it.isWinning(calledNumbers) }?.apply {
            val nonCalledNumbers = this.numbersByCol.flatten().toMutableList().apply { removeAll(calledNumbers) }
            println(calledNumbers.last() * nonCalledNumbers.sum())
        }
        board != null
    }
}
