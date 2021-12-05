package adventofcode2021.day05

import java.lang.Integer.signum
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class VentLineWithDiag(
        val x1: Int,
        val y1: Int,
        val x2: Int,
        val y2: Int
) {
    constructor(p1: Pos, p2: Pos) : this(p1.x, p1.y, p2.x, p2.y)

    fun getPos() =
            if (x1 == x2)
                (min(y1, y2)..max(y1, y2)).map { y -> Pos(x1, y) }
            else if (y1 == y2)
                (min(x1, x2)..max(x1, x2)).map { x -> Pos(x, y1) }
            else {
                val xDir = signum(x2 - x1)
                val yDir = signum(y2 - y1)
                val length = abs(x2 - x1)
                (0..length).map {
                    Pos(x1 + it * xDir, y1 + it * yDir)
                }
            }
}