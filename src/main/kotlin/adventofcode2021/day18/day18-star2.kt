package adventofcode2021.day18

import java.io.File

fun main(vararg args: String) {
    val inputs = File("data").readLines()

    val max =
            inputs.indices.map { i ->
                inputs.indices.map { j ->
                    if (j == i) 0
                    else {
                        val firstExp = inputs[i]
                        val secondExp = inputs[j]
                        val op = Operation.add(firstExp, secondExp)
                        val magnitude = Magnitude.getFromExpression(op)
                        magnitude
                    }
                }
            }.flatten().max()

    println(max)
}
