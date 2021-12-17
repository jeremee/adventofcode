package adventofcode2021.day14

import java.io.File

fun main(vararg args: String) {
    val inputs = File("data").readLines()
    val polymers = inputs[0].toCharArray().toMutableList()

    val insertion = inputs.drop(2)
            .map {
                val (pair, inserted) = it.split(" -> ")
                pair.toCharArray().toList() to inserted.toCharArray().first()
            }.toMap()

    (1..10).forEach {
        println(polymers)
        for (i in polymers.size-1 downTo 1) {
            val toInsert = insertion[polymers.subList(i - 1, i + 1)]
            if (toInsert != null) polymers.add(i, toInsert)

        }
    }

    val countByChar = polymers.distinct().map {c ->
        c to polymers.count { it == c }
    }

    println(countByChar.maxBy { it.second }!!.second - countByChar.minBy { it.second }!!.second )
}