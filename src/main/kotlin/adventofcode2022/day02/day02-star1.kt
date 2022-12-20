package adventofcode2022.day02

import adventofcode2022.day02.RPS_ME.*
import java.io.File

fun main(vararg args: String) {

    val result = File("data").readLines()
        .map {
            val values = it.split(" ")
            val adv = RPS_ADV.valueOf(values.first())
            val me = valueOf(values.last())
            adv to me
        }.map { (adv, me) ->
            val score = when {
                me == adv.loose -> 0
                me == adv.draw -> 3
                me == adv.win -> 6
                else -> error("unknown $adv and $me")
            }
            me.bonus + score
        }.sum()

    println(result)
}

enum class RPS_ADV(val win: RPS_ME, val draw: RPS_ME, val loose: RPS_ME) {
    A(Y, X, Z),
    B(Z, Y, X),
    C(X, Z, Y)
}

enum class RPS_ME(val bonus: Int) {
    X(1), Y(2), Z(3)
}
