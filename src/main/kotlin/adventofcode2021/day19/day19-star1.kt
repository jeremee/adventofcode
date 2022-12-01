package adventofcode2021.day19

import java.io.File

fun main(vararg args: String) {
    val inputs = File("data").readLines()

    val op = inputs.reduce { firstOp, secondOp -> Operation.add(firstOp, secondOp) }

    println(op)
    println(Magnitude.getFromExpression(op))
}
