package adventofcode2022.day02

import adventofcode2022.day02.Result.*
import java.io.File

fun main(vararg args: String) {

    val result = File("data").readLines()
        .map {
            val values = it.split(" ")
            val opponentChoice = RPS.valueOf(values.first())
            val wantedResult = valueOf(values.last())
            opponentChoice to wantedResult
        }.map { (opponentChoice, wantedResult) ->
            val myChoice = when (wantedResult) {
                X -> opponentChoice.loose()
                Y -> opponentChoice.draw()
                Z -> opponentChoice.win()
            }
            myChoice.bonus + wantedResult.score
        }.sum()

    println(result)
}

enum class RPS(val bonus: Int) {
    A(1),
    B(2),
    C(3);

    fun win() = when (this) {
        A -> B
        B -> C
        C -> A
    }

    fun draw() = when (this) {
        A -> A
        B -> B
        C -> C
    }

    fun loose() = when (this) {
        A -> C
        B -> A
        C -> B
    }
}

enum class Result(val score: Int) {
    X(0), Y(3), Z(6)
}
